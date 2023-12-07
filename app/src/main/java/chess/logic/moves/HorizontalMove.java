package chess.logic.moves;

import common.models.Board;
import common.models.Coordinate;
import common.models.SideColor;
import common.moves.Move;
import common.results.CheckResult;
import org.jetbrains.annotations.NotNull;


public class HorizontalMove implements Move {
    int columnsIncremented;
    private boolean limitless;
    public HorizontalMove(int columnsIncremented) {
        if (columnsIncremented == 0){
            limitless = true;
        }
        this.columnsIncremented = columnsIncremented;
    }
    public HorizontalMove(){
        limitless = true;
    }
    @Override
    public CheckResult<Coordinate,Boolean> checkMove(Coordinate initialSquare, Coordinate finalSquare, Board board, SideColor side) {
        checkLimitless(board);
        if(finalSquare.column() > initialSquare.column()){
            return moveRight(initialSquare, finalSquare, board);
        }
        else if(finalSquare.column() < initialSquare.column()){
            return moveLeft(initialSquare, finalSquare, board);
        }
        return new CheckResult<>(finalSquare, false,"Horizontal Movement Failed");
    }

    @NotNull
    private CheckResult<Coordinate, Boolean> moveLeft(Coordinate initialSquare, Coordinate finalSquare, Board board) {
        for(int i = 1; i < initialSquare.column() - finalSquare.column(); i++){
            Coordinate coordinate = new Coordinate(initialSquare.column() -i, initialSquare.row());
            if(board.checkForPieceInSquare(coordinate)){
                return new CheckResult<>(finalSquare, false, "Horizontal Movement Failed");
            }
        }
        if (limitless){
            if (finalSquare.row() == initialSquare.row()){
                return new CheckResult<>(finalSquare, true, "Horizontal Movement Successful");
            } else {
                return new CheckResult<>(finalSquare, false, "Horizontal Movement Failed");
            }
        } else {
            if (checkIfCanMoveLeft(initialSquare, finalSquare)) {
                return new CheckResult<>(finalSquare, true, "Horizontal Movement Successful");
            } else {
                return new CheckResult<>(finalSquare, false, "Horizontal Movement Failed");
            }
        }
    }

    private boolean checkIfCanMoveLeft(Coordinate initialSquare, Coordinate finalSquare) {
        return finalSquare.row() == initialSquare.row() && finalSquare.column() == initialSquare.column() - columnsIncremented;
    }

    @NotNull
    private CheckResult<Coordinate, Boolean> moveRight(Coordinate initialSquare, Coordinate finalSquare, Board board) {
        for(int i = 1; i < finalSquare.column() - initialSquare.column(); i++){
            Coordinate coordinate = new Coordinate(initialSquare.column() +i, initialSquare.row());
            if(board.checkForPieceInSquare(coordinate)){
                return new CheckResult<>(finalSquare, false, "Horizontal Movement Failed");
            }
        }
        if (limitless){
            if (finalSquare.row() == initialSquare.row()){
                return new CheckResult<>(finalSquare, true, "Horizontal Movement Successful");
            } else {
                return new CheckResult<>(finalSquare, false, "Horizontal Movement Failed");
            }
        } else {
            if (checkIfCanMoveRight(initialSquare, finalSquare)){
                return new CheckResult<>(finalSquare, true, "Horizontal Movement Successful");
            } else {
                return new CheckResult<>(finalSquare, false, "Horizontal Movement Failed");
            }
        }
    }

    private boolean checkIfCanMoveRight(Coordinate initialSquare, Coordinate finalSquare) {
        return finalSquare.row() == initialSquare.row() && finalSquare.column() == initialSquare.column() + columnsIncremented;
    }

    private void checkLimitless(Board board) {
        if (limitless){
            columnsIncremented = board.getColumns();
        }
    }
}