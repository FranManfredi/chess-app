package chess.logic.moves;

import common.models.Board;
import common.models.Coordinate;
import common.models.SideColor;
import common.moves.Move;
import common.results.CheckResult;

public class JumpMove implements Move {
    private final int rowsIncremented;
    private final int columnIncremented;

    public JumpMove(int rowsIncremented, int columnIncremented) {
        this.rowsIncremented = rowsIncremented;
        this.columnIncremented = columnIncremented;
    }
    @Override
    public CheckResult<Coordinate,Boolean> checkMove(Coordinate initialSquare, Coordinate finalSquare, Board board, SideColor color) {
        if (isMovingCorrectAmmount(initialSquare, finalSquare)){
            return new CheckResult<>(finalSquare, true,"Jump Movement Successful");
        } else {
            return new CheckResult<>(finalSquare, false,"Jump Movement Failed");
        }
    }

    private boolean isMovingCorrectAmmount(Coordinate initialSquare, Coordinate finalSquare) {
        return finalSquare.column() == initialSquare.column() + columnIncremented && finalSquare.row() == initialSquare.row() + rowsIncremented;
    }

}
