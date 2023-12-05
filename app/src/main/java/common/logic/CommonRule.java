package common.logic;


import common.models.Coordinate;
import common.models.Board;
import common.models.Piece;

import java.util.Objects;

public class CommonRule {
    public Boolean checkRule(Board board, Piece piece, Coordinate toSquare) {
        if (isInBoard(board, toSquare)) {
            return false;
        }
        if (isNotSamePlace(board, piece, toSquare)){
            return false;
        }
        return isNotNull(board, toSquare) || isNotSameColor(board, piece, toSquare);
    }

    private boolean isNotSameColor(Board board, Piece piece, Coordinate toSquare) {
        return !Objects.equals(board.getPiece(toSquare).successfulResult().get().getColor(), piece.getColor());
    }

    private boolean isNotNull(Board board, Coordinate toSquare) {
        return Objects.equals(board.getPiece(toSquare).successfulResult().get().getName(), "null");
    }

    private boolean isNotSamePlace(Board board, Piece piece, Coordinate toSquare) {
        return toSquare.column() == board.getCoordOfPiece(piece).successfulResult().get().column() && toSquare.row() == board.getCoordOfPiece(piece).successfulResult().get().row();
    }

    private boolean isInBoard(Board board, Coordinate toSquare) {
        return toSquare.column() > board.getColumns() || toSquare.row() > board.getRows() || toSquare.column() <= 0 || toSquare.row() <= 0;
    }

}
