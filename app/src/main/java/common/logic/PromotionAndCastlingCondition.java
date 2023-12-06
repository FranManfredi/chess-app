package common.logic;

import common.models.Board;
import common.models.Coordinate;
import common.models.Piece;
import common.results.GetResult;

public class PromotionAndCastlingCondition implements SpecialCondition{
    @Override
    public Board getBoard(Board board, Piece piece, Coordinate toSquare) {
        return null;
    }
    @Override
    public boolean checkCondition(Board board, Piece piece, Coordinate toSquare) {
        if (piece.getName().equals("king") && board.getPiece(toSquare).successfulResult().get().getName().equals("rook")) {
            return isPathClear(board, piece, toSquare);
        }
        return false;
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
        return checkCondition(board, piece, toSquare);
    }
}
