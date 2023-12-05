package common.models;

import common.results.GetResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class Board {
    private final Square[] squares;
    private final List<PieceCoord> pieces;
    private final int column;
    private final int row;
    private final List<MovementHistory> movements;
    PieceFactory pieceFactory;

    public Board(int row, int column, List<PieceCoord> blackPieces, List<PieceCoord> whitePieces, PieceFactory pieceFactory) {
        squares = new Square[row * column];
        whitePieces.addAll(blackPieces);
        this.pieces = whitePieces;
        this.column = column;
        this.row = row;
        this.movements = new ArrayList<>();
        this.pieceFactory = pieceFactory;

        for (int i = 1; i <= row; i++) {
            for (int j = 1; j <= column; j++) {
                int index = (i - 1) * column + j - 1;
                squares[index] = new Square(new Coordinate(j, i), pieceFactory.createNullPiece());
            }
        }
        for (PieceCoord piece : whitePieces) {
            int initialRow = piece.coord().row();
            int initialColumn = piece.coord().column();
            int index = (initialRow - 1) * column + initialColumn - 1;
            Square square = squares[index];
            square.setPiece(piece.piece());
        }
    }
    public Board(int row, int column, List<PieceCoord> pieces, Square[] squares, List<MovementHistory> movements, PieceFactory pieceFactory) {
        this.row = row;
        this.column = column;
        this.squares = squares;
        this.pieces = pieces;
        this.movements = new ArrayList<>(movements);
        this.pieceFactory = pieceFactory;
    }

    public Board positionPiece(Piece piece, Coordinate position) {
        Square square = squares[this.column * (position.row() - 1) + (position.column() - 1)];
        square = new Square(square.getCoordinate(), piece);
        Square[] newSquares = this.squares.clone();
        newSquares[this.column * (position.row() - 1) + (position.column() - 1)] = square;
        return new Board(row,column,this.pieces,newSquares,this.getMovements(), pieceFactory);
    }


    public GetResult<Coordinate> getCoordOfPiece(Piece piece) {
        for (Square square : squares) {
            Piece squarePiece = square.getPiece();
            if (piece.getId() == squarePiece.getId()) {
                return new GetResult<>(Optional.of(square.getCoordinate()));
            }
        }
        return new GetResult<>(Optional.empty());
    }

    public GetResult<Piece> getPiece(Coordinate coord){
        for(Square square : squares){
            if(square.getCoordinate().equals(coord)){
                return new GetResult<>(Optional.of(square.getPiece()));
            }
        }
        return new GetResult<>(Optional.empty());
    }

    public GetResult<Piece> findImportantPiece(SideColor color){
        for(PieceCoord p : pieces){
            if(p.piece().isImportant() && p.piece().getColor() == color){
                return new GetResult<>(Optional.of(p.piece()));
            }
        }
        return new GetResult<>(Optional.empty());
    }

    public int getRows() {
        return row;
    }

    public boolean checkForPieceInSquare(Coordinate coordinate) {
        if (isOutOfBounds(coordinate)) {
            return false;
        }
        return !Objects.equals(getPiece(coordinate).successfulResult().get().getName(), "null");
    }

    private boolean isOutOfBounds(Coordinate coordinate) {
        return coordinate.row() > row || coordinate.column() > column || coordinate.row() < 1 || coordinate.column() < 1;
    }

    public int getColumns() {
        return column;
    }

    public List<PieceCoord> getPieces() {
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

    public PieceFactory getPieceBuilder() {
        return pieceFactory;
    }
}
