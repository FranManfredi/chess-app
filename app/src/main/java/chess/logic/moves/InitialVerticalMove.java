package chess.logic.moves;

import common.models.*;
import common.moves.Move;
import common.results.CheckResult;

import java.util.List;
import java.util.Objects;

public class InitialVerticalMove implements Move {

    int rowsIncremented;
    private final boolean backwardMove;
    private final boolean limitless;

    public InitialVerticalMove(int rowsIncremented, boolean backwardMove) {
        limitless = false;
        this.rowsIncremented = rowsIncremented;
        this.backwardMove = backwardMove;
    }

    @Override
    public CheckResult<Coordinate,Boolean> checkMove(Coordinate initialSquare, Coordinate finalSquare, Board board, SideColor side) {
        checkLimitless(board);
        boolean moved = findIfMoved(board.getMovements(), initialSquare);
        if (moved){
            return new CheckResult<>(finalSquare, false,"Vertical Movement Failed");
        }
        if (backwardMove) if (checkBackwardMove(initialSquare, finalSquare, board)) {
            return new CheckResult<>(finalSquare, true, "Vertical Movement Successful");
        } else {
            return new CheckResult<>(finalSquare, false, "Vertical Movement Failed");
        }
        if (Objects.equals(side, SideColor.White)) {
            if (isPathBlockedForward(initialSquare, finalSquare, board)){
                return new CheckResult<>(finalSquare, true,"Vertical Movement Successful");
            } else {
                return new CheckResult<>(finalSquare, false,"Vertical Movement Failed");
            }
        } else {
            if (isPathBlockedBackward(initialSquare, finalSquare, board)){
                return new CheckResult<>(finalSquare, true,"Vertical Movement Successful");

            } else {
                return new CheckResult<>(finalSquare, false,"Vertical Movement Failed");
            }
        }
    }

    private boolean findIfMoved(List<MovementHistory> movements, Coordinate initialSquare) {
        for (MovementHistory movement : movements) {
            if (movement.initialSquare().equals(initialSquare) || movement.finalSquare().equals(initialSquare)) {
                return true;
            }
        }
        return false;
    }

    private Boolean checkBackwardMove(Coordinate initialSquare, Coordinate finalSquare, Board board) {
        if (finalSquare.row() > initialSquare.row()) {
            return isPathBlockedForward(initialSquare, finalSquare, board);
        } else
            return isPathBlockedBackward(initialSquare, finalSquare, board);
    }

    public boolean isPathBlockedForward(Coordinate initialSquare, Coordinate finalSquare, Board board){
        for (int i = 1; i < finalSquare.row() - initialSquare.row(); i++) {
            Coordinate coordinate = new Coordinate(initialSquare.column(), initialSquare.row() +i);
            if (board.checkForPieceInSquare(coordinate)){
                return false;
            }
        }
        if (!limitless)
            return finalSquare.column() == initialSquare.column() && finalSquare.row() == initialSquare.row() + rowsIncremented;
        else
            return finalSquare.column() == initialSquare.column();

    }
    public boolean isPathBlockedBackward(Coordinate initialSquare, Coordinate finalSquare, Board board){
        for (int i = 1; i < initialSquare.row() - finalSquare.row(); i++) {
            Coordinate coordinate = new Coordinate(initialSquare.column(), initialSquare.row() -i);
            if (board.checkForPieceInSquare(coordinate)){
                return false;
            }
        }
        if (!limitless)
            return finalSquare.column() == initialSquare.column() && finalSquare.row() == initialSquare.row() - rowsIncremented;
        else
            return finalSquare.column() == initialSquare.column();
    }

    private void checkLimitless(Board board) {
        if (limitless){
            rowsIncremented = board.getRows();
        }
    }


}