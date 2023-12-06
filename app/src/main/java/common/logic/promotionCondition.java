package common.logic;

import common.models.Board;
import common.models.Coordinate;
import common.models.Piece;

public class promotionCondition implements SpecialCondition{

    @Override
    public Board getBoard(Board board, Piece piece, Coordinate toSquare) {
        return board.positionPiece(board.getPieceBuilder().promotePawn(piece.getColor(), piece.getId()), toSquare);
    }

    @Override
    public boolean checkCondition(Board board, Piece piece, Coordinate toSquare) {
        return pawnReachedEnd(piece, toSquare, board);
    }

    @Override
    public boolean overrideCommonRule(Board board, Piece piece, Coordinate toSquare) {
        return false;
    }

    private boolean pawnReachedEnd(Piece pieceMoving, Coordinate coordinate, Board board) {
        // Check if a pawn reached the end of the board
        return pieceMoving.getName().equals("pawn") && (coordinate.row() == 1 || coordinate.row() == board.getRows());
    }
}
