package chess.moves;

import common.models.ChessBoard;
import common.models.Coordinate;
import common.models.SideColor;
import common.moves.Move;
import common.results.CheckResult;

import java.util.Objects;

public class VerticalMove implements Move {

    private int rowsIncrement;
    private final boolean backwardMove;
    private final boolean isLimitless;

    public VerticalMove(int rowsIncrement, boolean backwardMove) {
        this.isLimitless = false;
        this.rowsIncrement = rowsIncrement;
        this.backwardMove = backwardMove;
    }

    public VerticalMove(boolean backwardMove) {
        this.isLimitless = true;
        this.backwardMove = backwardMove;
    }

    @Override
    public CheckResult<Coordinate, Boolean> checkMove(Coordinate initialSquare, Coordinate finalSquare, ChessBoard board, SideColor side) {
        checkLimitless(board);

        if (backwardMove) {
            if (checkBackwardMove(initialSquare, finalSquare, board)) {
                return new CheckResult<>(finalSquare, true, "Vertical Movement Successful");
            } else {
                return new CheckResult<>(finalSquare, false, "Vertical Movement Failed");
            }
        }

        if (Objects.equals(side, SideColor.White) && isPathBlockedForward(initialSquare, finalSquare, board)) {
            return new CheckResult<>(finalSquare, true, "Vertical Movement Successful");
        } else if (isPathBlockedBackward(initialSquare, finalSquare, board)) {
            return new CheckResult<>(finalSquare, true, "Vertical Movement Successful");
        } else {
            return new CheckResult<>(finalSquare, false, "Vertical Movement Failed");
        }
    }

    @Override
    public int getRowsIncremented() {
        return rowsIncrement;
    }

    @Override
    public int getColumnIncremented() {
        return 0;
    }

    private boolean checkBackwardMove(Coordinate initialSquare, Coordinate finalSquare, ChessBoard board) {
        if (finalSquare.row() > initialSquare.row()) {
            return isPathBlockedForward(initialSquare, finalSquare, board);
        } else {
            return isPathBlockedBackward(initialSquare, finalSquare, board);
        }
    }

    private boolean isPathBlockedForward(Coordinate initialSquare, Coordinate finalSquare, ChessBoard board) {
        for (int i = 1; i < finalSquare.row() - initialSquare.row(); i++) {
            Coordinate coordinate = new Coordinate(initialSquare.column(), initialSquare.row() + i);
            if (board.checkForPieceInSquare(coordinate)) {
                return false;
            }
        }
        return isLimitless || (finalSquare.column() == initialSquare.column() && finalSquare.row() == initialSquare.row() + rowsIncrement);
    }

    private boolean isPathBlockedBackward(Coordinate initialSquare, Coordinate finalSquare, ChessBoard board) {
        for (int i = 1; i < initialSquare.row() - finalSquare.row(); i++) {
            Coordinate coordinate = new Coordinate(initialSquare.column(), initialSquare.row() - i);
            if (board.checkForPieceInSquare(coordinate)) {
                return false;
            }
        }
        return isLimitless || (finalSquare.column() == initialSquare.column() && finalSquare.row() == initialSquare.row() - rowsIncrement);
    }

    private void checkLimitless(ChessBoard board) {
        if (isLimitless) {
            rowsIncrement = board.getRows();
        }
    }
}
