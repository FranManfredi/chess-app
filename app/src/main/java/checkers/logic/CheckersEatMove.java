package checkers.logic;

import common.models.Board;
import common.models.Coordinate;
import common.models.SideColor;
import common.moves.Move;
import common.results.CheckResult;

public class CheckersEatMove implements Move {
    private final int rowsIncrement;
    private final int columnIncrement;

    public CheckersEatMove(int rowsIncrement, int columnIncrement) {
        this.rowsIncrement = rowsIncrement;
        this.columnIncrement = columnIncrement;
    }

    @Override
    public CheckResult<Coordinate, Boolean> checkMove(Coordinate initialSquare, Coordinate finalSquare, Board board, SideColor color) {
        int targetRow = (color == SideColor.White) ? finalSquare.row() + rowsIncrement : finalSquare.row() - rowsIncrement;

        if (isValidColumnDirection(initialSquare, finalSquare)) {
            Coordinate targetSquare = new Coordinate(finalSquare.column() + columnIncrement, targetRow);

            if (board.checkForPieceInSquare(targetSquare) || !isValidSquare(targetSquare, board)) {
                return new CheckResult<>(targetSquare, false, "Jump Movement Failed");
            }

            return new CheckResult<>(targetSquare, true, "Jump Movement Successful");
        }

        return new CheckResult<>(finalSquare, false, "Jump Movement Failed");
    }

    private boolean isValidSquare(Coordinate targetSquare, Board board) {
        int maxRow = board.getRows();
        int maxColumn = board.getColumns();

        return targetSquare.row() <= maxRow && targetSquare.row() > 0 &&
               targetSquare.column() <= maxColumn && targetSquare.column() > 0;
    }

    private boolean isValidColumnDirection(Coordinate initialSquare, Coordinate finalSquare) {
        int direction = finalSquare.column() - initialSquare.column();
        int directionSign = Integer.signum(direction);
        int incrementSign = Integer.signum(columnIncrement);

        return directionSign == incrementSign;
    }

    @Override
    public int getRowsIncremented() {
        return 0;
    }

    @Override
    public int getColumnIncremented() {
        return 0;
    }
}
