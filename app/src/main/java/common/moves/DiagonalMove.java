package common.moves;

import common.models.Board;
import common.models.Coordinate;
import common.models.SideColor;
import common.results.CheckResult;


public class DiagonalMove implements Move {
    private final int rowsIncremented;
    private final int columnIncremented;
    private final boolean backward;
    private int rowTemp;
    private int columnTemp;

    public DiagonalMove(int rowsIncremented, int columnIncremented, boolean backward) {
        this.rowsIncremented = rowsIncremented;
        this.columnIncremented = columnIncremented;
        this.backward = backward;
    }
    public DiagonalMove() {
        this.rowsIncremented = 0;
        this.columnIncremented = 0 ;
        this.backward = true;
    }



    @Override
    public CheckResult<Coordinate,Boolean> checkMove(Coordinate initialSquare, Coordinate finalSquare, Board board, SideColor color) {

        if (isNotMovingSameAmmountHorizontalAndVertical(initialSquare, finalSquare))
            return new CheckResult<>(finalSquare, false,"Diagonal Movement Failed");
        else if (isGoingBackwardsIllegally(initialSquare, finalSquare, color)) {
            return new CheckResult<>(finalSquare, false,"Diagonal Movement Failed");
        }
        rowTemp = checkRowTemp(initialSquare, finalSquare);
        columnTemp = checkColumnTemp(initialSquare, finalSquare);
        checkForDirection(initialSquare, finalSquare);
        if(isDiagonalClear(board,initialSquare, finalSquare))
            if (isMovingCorrectAmmount(initialSquare, finalSquare)){
                return new CheckResult<>(finalSquare, true,"Diagonal Movement Successful");
            } else {
                return new CheckResult<>(finalSquare, false,"Diagonal Movement Failed");
            }
        return new CheckResult<>(finalSquare, false,"Diagonal Movement Failed");
    }

    private boolean isMovingCorrectAmmount(Coordinate initialSquare, Coordinate finalSquare) {
        return finalSquare.column() == initialSquare.column() + columnTemp && finalSquare.row() == initialSquare.row() + rowTemp;
    }

    private static boolean isNotMovingSameAmmountHorizontalAndVertical(Coordinate initialSquare, Coordinate finalSquare) {
        return Math.abs(finalSquare.column() - initialSquare.column()) != Math.abs(finalSquare.row() - initialSquare.row());
    }

    private boolean isGoingBackwardsIllegally(Coordinate initialSquare, Coordinate finalSquare, SideColor color) {
        if (backward){
            return false;
        } else if ( color == SideColor.White) {
            return initialSquare.row() > finalSquare.row();
        }
        return initialSquare.row() < finalSquare.row();
    }

    private int checkColumnTemp(Coordinate initialSquare, Coordinate finalSquare) {
        if (columnIncremented != 0) {
            return columnIncremented;
        } else {
            return Math.abs(finalSquare.column() - initialSquare.column());
        }
    }

    private int checkRowTemp(Coordinate initialSquare, Coordinate finalSquare) {
        if (rowsIncremented != 0) {
            return rowsIncremented;
        } else {
            return Math.abs(finalSquare.row() - initialSquare.row());
        }
    }


    public boolean isDiagonalClear(Board board, Coordinate initialSquare, Coordinate finalSquare) {
        int rowsCount = Integer.compare(finalSquare.row(), initialSquare.row());
        int columnCount = Integer.compare(finalSquare.column(), initialSquare.column());

        for (int i = 1; i < Math.abs(finalSquare.row() - initialSquare.row()); i++) {
            int rowToCheck = initialSquare.row() + i * rowsCount;
            int colToCheck = initialSquare.column() + i * columnCount;
            Coordinate coordinate = new Coordinate(colToCheck, rowToCheck);

            if (board.checkForPieceInSquare(coordinate)) {
                return false;
            }
        }
        return true;
    }

    private void checkForDirection(Coordinate initialSquare, Coordinate finalSquare) {
        if(finalSquare.column() < initialSquare.column()){
            columnTemp *= -1;
        }
        if(finalSquare.row() < initialSquare.row()){
            rowTemp *= -1;
        }
    }
}