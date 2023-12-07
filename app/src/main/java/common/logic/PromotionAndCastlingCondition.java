package common.logic;

import common.models.*;
import common.results.GetResult;

import java.util.Arrays;
import java.util.List;

public class PromotionAndCastlingCondition implements SpecialCondition{
    @Override
    public Board getBoard(Board board, Piece piece, Coordinate toSquare){
        if (pawnReachedEnd(piece, toSquare, board)) {
            return board.positionPiece(board.getPieceBuilder().promotePawn(piece.getColor(), piece.getId()), toSquare);
        }
        Coordinate kingCoordinate = board.getCoordOfPiece(piece).successfulResult().get();
        Piece rook = board.getPiece(toSquare).successfulResult().get();

        if (kingCoordinate.column() < toSquare.column()) {
            return castleRight(board, piece, kingCoordinate, rook);
        } else {
            return castleLeft(board, piece, kingCoordinate, rook);
        }
    }

    private Board castleLeft(Board board, Piece piece, Coordinate kingCoordinate, Piece rook) {
        Board newBoard = board.positionPiece(piece, new Coordinate(kingCoordinate.column() - 2, kingCoordinate.row()));
        newBoard = newBoard.positionPiece(board.getPieceBuilder().createNullPiece(), board.getCoordOfPiece(piece).successfulResult().get());
        newBoard = newBoard.positionPiece(board.getPieceBuilder().createNullPiece(), board.getCoordOfPiece(rook).successfulResult().get());
        newBoard = newBoard.positionPiece(rook, new Coordinate(kingCoordinate.column() - 1, kingCoordinate.row()));
        return newBoard;
    }
    private Board castleRight(Board board, Piece piece, Coordinate kingCoordinate, Piece rook) {
        Board newBoard = board.positionPiece(piece, new Coordinate(kingCoordinate.column() + 2, kingCoordinate.row()));
        newBoard = newBoard.positionPiece(board.getPieceBuilder().createNullPiece(), board.getCoordOfPiece(piece).successfulResult().get());
        newBoard = newBoard.positionPiece(rook, new Coordinate(kingCoordinate.column() + 1, kingCoordinate.row()));
        newBoard = newBoard.positionPiece(board.getPieceBuilder().createNullPiece(), board.getCoordOfPiece(rook).successfulResult().get());
        return newBoard;
    }

    @Override
    public boolean checkCondition(Board board, Piece piece, Coordinate toSquare) {
        if (pawnReachedEnd(piece, toSquare, board)) {
            return true;
        }
        else if (piece.getName().equals("king") && board.getPiece(toSquare).successfulResult().get().getName().equals("rook")) {
            Piece rook = board.getPiece(toSquare).successfulResult().get();
            if (kingOrRookMoved(board, piece, rook)) {
                return false;
            }
            return isPathClear(board, piece, toSquare);
        }
        return false;
    }

    private boolean kingOrRookMoved(Board board, Piece king, Piece rook) {
        List<MovementHistory> movements = board.getMovements();
        Coordinate rookCoordinate = board.getCoordOfPiece(rook).successfulResult().get();
        for (MovementHistory movement : movements) {
            if (movement.piece().getName().equals("king") && movement.piece().getId() == king.getId()) {
                return true;
            }
            if (movement.initialSquare().equals(rookCoordinate) || movement.finalSquare().equals(rookCoordinate)) {
                return true;
            }
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
            if (kingOrRookMoved(board, piece, board.getPiece(toSquare).successfulResult().get())) {
                return false;
            }
            return isPathClear(board, piece, toSquare);
        }
        return false;
    }
}
