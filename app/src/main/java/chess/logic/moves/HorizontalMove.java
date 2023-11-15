package chess.moves;

import common.models.ChessBoard;
import common.models.Coordinate;
import common.models.SideColor;
import common.moves.Move;
import common.results.CheckResult;

public class HorizontalMove implements Move {

    private final int columnsIncrement;
    private final boolean canJump;
    private final boolean isLimitless;

    public HorizontalMove(int columnsIncrement, boolean canJump) {
        this.columnsIncrement = columnsIncrement == 0 ? ChessBoard.MAX_COLUMNS : columnsIncrement;
        this.canJump = canJump;
        this.isLimitless = columnsIncrement == 0;
    }

    public HorizontalMove(boolean canJump) {
        this.columnsIncrement = ChessBoard.MAX_COLUMNS;
        this.canJump = canJump;
        this.isLimitless = true;
    }

    @Override
    public CheckResult<Coordinate, Boolean> checkMove(Coordinate initialSquare, Coordinate finalSquare, ChessBoard board, SideColor side) {
        checkLimitless(board);

        int columnDifference = finalSquare.column() - initialSquare.column();
        int increment = columnDifference > 0 ? 1 : -1;

        for (int i = 1; i < Math.abs(columnDifference); i++) {
            Coordinate coordinate = new Coordinate(initialSquare.column() + i * increment, initialSquare.row());
            if (board.checkForPieceInSquare(coordinate)) {
                return new CheckResult<>(finalSquare, false, "Horizontal Movement Failed");
            }
        }

        return (finalSquare.row() == initialSquare.row())
                ? new CheckResult<>(finalSquare, true, "Horizontal Movement Successful")
                : new CheckResult<>(finalSquare, false, "Horizontal Movement Failed");
    }

    @Override
    public int getRowsIncremented() {
        return 0;
    }

    @Override
    public int getColumnIncremented() {
        return columnsIncrement;
    }

    private void checkLimitless(ChessBoard board) {
        if (isLimitless) {
            columnsIncrement = board.getColumns();
        }
    }
}
