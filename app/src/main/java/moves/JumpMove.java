package chess.moves;

import common.models.ChessBoard;
import common.models.Coordinate;
import common.models.SideColor;
import common.moves.Move;
import common.results.CheckResult;

public class JumpMove implements Move {

    private final int rowsIncrement;
    private final int columnIncrement;

    public JumpMove(int rowsIncrement, int columnIncrement) {
        this.rowsIncrement = rowsIncrement;
        this.columnIncrement = columnIncrement;
    }

    @Override
    public CheckResult<Coordinate, Boolean> checkMove(Coordinate initialSquare, Coordinate finalSquare, ChessBoard board, SideColor color) {
        boolean isJumpSuccessful = finalSquare.column() == initialSquare.column() + columnIncrement
                && finalSquare.row() == initialSquare.row() + rowsIncrement;

        return new CheckResult<>(finalSquare, isJumpSuccessful, isJumpSuccessful ? "Jump Movement Successful" : "Jump Movement Failed");
    }

    @Override
    public int getRowsIncremented() {
        return rowsIncrement;
    }

    @Override
    public int getColumnIncremented() {
        return columnIncrement;
    }
}