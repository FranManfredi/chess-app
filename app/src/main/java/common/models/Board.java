package common.models;

import common.results.GetResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class Board {
    private final Square[] squares;
    private final List<Piece> pieces;
    private final int column;
    private final int row;
    private final List<MovementHistory> movements;
    PieceBuilder pieceBuilder;

    public Board(int row, int column, List<Piece> blackPieces, List<Piece> whitePieces, PieceBuilder pieceBuilder) {
        squares = new Square[row * column];
        whitePieces.addAll(blackPieces);
        this.pieces = whitePieces;
        this.column = column;
        this.row = row;
        this.movements = new ArrayList<>();
        this.pieceBuilder = pieceBuilder;

        for (int i = 1; i <= row; i++) {
            for (int j = 1; j <= column; j++) {
                int index = (i - 1) * column + j - 1;
                squares[index] = new Square(new Coordinate(j, i), pieceBuilder.createNullPiece(new Coordinate(j, i)));
            }
        }
        for (Piece piece : whitePieces) {
            int initialRow = piece.getInitialSquare().row();
            int initialColumn = piece.getInitialSquare().column();
            int index = (initialRow - 1) * column + initialColumn - 1;
            Square square = squares[index];
            square.setPiece(piece);
        }
    }
    public Board(int row, int column, List<Piece> pieces, Square[] squares, List<MovementHistory> movements, PieceBuilder pieceBuilder) {
        this.row = row;
        this.column = column;
        this.squares = squares;
        this.pieces = pieces;
        this.movements = new ArrayList<>(movements);
        this.pieceBuilder = pieceBuilder;
    }

    public Board positionPiece(Piece piece, Coordinate position) {
        Square square = squares[this.column * (position.row() - 1) + (position.column() - 1)];
        square = new Square(square.getCoordinate(), piece);
        Square[] newSquares = this.squares.clone();
        newSquares[this.column * (position.row() - 1) + (position.column() - 1)] = square;
        return new Board(row,column,this.pieces,newSquares,this.getMovements(),pieceBuilder);
    }


    public GetResult<Coordinate,Boolean> getSquareOfPiece(Piece piece) {
        for (Square square : squares) {
            Piece squarePiece = square.getPiece();
            if (piece.getId() == squarePiece.getId()) {
                return new GetResult<>(Optional.of(square.getCoordinate()),false);
            }
        }
        return new GetResult<>(Optional.empty(),true);
    }

    public GetResult<Piece,Boolean> findImportantPiece(SideColor color){
        for(Piece p : pieces){
            if(p.isImportant() && p.getColor() == color){
                return new GetResult<>(Optional.of(p),false);
            }
        }
        return new GetResult<>(Optional.empty(),true);
    }

    public Square getSquare(Coordinate coordinate) {
        int adjustedRow = coordinate.row() - 1;
        int adjustedColumn = coordinate.column() - 1;
        int index = this.column * adjustedRow + adjustedColumn;
        return squares[index];
    }

    public int getRows() {
        return row;
    }

    public boolean checkForPieceInSquare(Coordinate coordinate) {
        if (coordinate.row() > row || coordinate.column() > column || coordinate.row() < 1 || coordinate.column() < 1) {
            return false;
        }
        return !Objects.equals(getSquare(coordinate).getPiece().getName(), "null");
    }

    public int getColumns() {
        return column;
    }

    public List<Piece> getPieces() {
        return pieces;
    }

    public Square[] getSquares() {
        return squares;
    }

    public List<MovementHistory> getMovements() {
        return movements;
    }


    public List<Piece> getCurrentPieces() {
        List<Piece> currentPieces = new ArrayList<>();
        for(Square square : squares){
            if(!Objects.equals(square.getPiece().getName(), "null")){
                currentPieces.add(square.getPiece());
            }
        }
        return currentPieces;
    }

    public PieceBuilder getPieceBuilder() {
        return pieceBuilder;
    }
}
