package common.logic;

import common.models.Board;
import common.models.Coordinate;
import common.models.Piece;
import common.results.GetResult;

public class PromotionAndCastlingCondition implements SpecialCondition{
    @Override
    public Board getBoard(Board board, Piece piece, Coordinate toSquare){
        if (pawnReachedEnd(piece, toSquare, board)) {
            return board.positionPiece(board.getPieceBuilder().promotePawn(piece.getColor(), piece.getId()), toSquare);
        }
        return null;
    }
    @Override
    public boolean checkCondition(Board board, Piece piece, Coordinate toSquare) {
        if (pawnReachedEnd(piece, toSquare, board)) {
            return true;
        }
        else if (piece.getName().equals("king") && board.getPiece(toSquare).successfulResult().get().getName().equals("rook")) {
            return isPathClear(board, piece, toSquare);
        }
        return false;
    }

    private boolean pawnReachedEnd(Piece pieceMoving, Coordinate coordinate, Board board) {
        // Check if a pawn reached the end of the board
        return pieceMoving.getName().equals("pawn") && (coordinate.row() == 1 || coordinate.row() == board.getRows());
    }

    private boolean isPathClear(Board board, Piece piece, Coordinate toSquare) {
        Coordinate kingCoordinate = board.getCoordOfPiece(piece).successfulResult().get();

        if (kingCoordinate.column() < toSquare.column()) {
            return isPathClearRight(board, kingCoordinate, toSquare);
        } else {
            return isPathClearLeft(board, kingCoordinate, toSquare);
        }
    }

    private boolean isPathClearLeft(Board board, Coordinate kingCoordinate, Coordinate toSquare) {
        for (int i = kingCoordinate.column() - 1; i > toSquare.column(); i--) {
            if (board.checkForPieceInSquare(new Coordinate(i, kingCoordinate.row()))) {
                return false;
            }
        }
        return true;
    }

    private boolean isPathClearRight(Board board, Coordinate kingCoordinate, Coordinate toSquare) {
        for (int i = kingCoordinate.column() + 1; i < toSquare.column(); i++) {
            if (board.checkForPieceInSquare(new Coordinate(i, kingCoordinate.row()))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean overrideCommonRule(Board board, Piece piece, Coordinate toSquare) {
        if (pawnReachedEnd(piece, toSquare, board)) {
            return false;
        }
        else if (piece.getName().equals("king") && board.getPiece(toSquare).successfulResult().get().getName().equals("rook")) {
            return isPathClear(board, piece, toSquare);
        }
        return false;
    }
}
