package chess.logic.moves;

import common.models.Board;
import common.models.Coordinate;
import common.models.SideColor;
import common.moves.Move;
import common.results.CheckResult;


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
            for(int i = 1; i < finalSquare.column() - initialSquare.column(); i++){
                Coordinate coordinate = new Coordinate(initialSquare.column() +i, initialSquare.row());
                if(board.checkForPieceInSquare(coordinate)){
                    return new CheckResult<>(finalSquare, false,"Horizontal Movement Failed");
                }
            }
            if (finalSquare.row() == initialSquare.row()){
                return new CheckResult<>(finalSquare, true,"Horizontal Movement Successful");
            } else {
                return new CheckResult<>(finalSquare, false,"Horizontal Movement Failed");
            }
        }
        else if(finalSquare.column() < initialSquare.column()){
            for(int i = 1; i < initialSquare.column() - finalSquare.column(); i++){
                Coordinate coordinate = new Coordinate(initialSquare.column() -i, initialSquare.row());
                if(board.checkForPieceInSquare(coordinate)){
                    return new CheckResult<>(finalSquare, false,"Horizontal Movement Failed");
                }
            }
            if (finalSquare.row() == initialSquare.row()){
                return new CheckResult<>(finalSquare, true,"Horizontal Movement Successful");
            } else {
                return new CheckResult<>(finalSquare, false,"Horizontal Movement Failed");
            }
        }
        return new CheckResult<>(finalSquare, false,"Horizontal Movement Failed");
    }
    private void checkLimitless(Board board) {
        if (limitless){
            columnsIncremented = board.getColumns();
        }
    }
}
