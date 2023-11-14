package chess.game.classic;

import common.models.ChessBoard;
import common.models.Coordinate;
import common.models.ChessPiece;
import common.models.SideColor;
import common.moves.Move;

public class CheckForCheck {

    public boolean check(ChessBoard board, SideColor color, ChessPiece piece, Coordinate toSquare) {
        if (!piece.isImportant()) {
            piece = board.findImportantPiece(color).successfulResult().orElse(null);
            toSquare = board.getSquareOfPiece(piece).successfulResult().orElse(null);
        }

        return isForCheck(board, color, toSquare);
    }

    public boolean isForCheck(ChessBoard board, SideColor color, Coordinate toSquare) {
        for (ChessPiece piece : board.getCurrentPieces()) {
            if (piece.getColor() != color && checkForEach(piece, toSquare, board)) {
                return true;
            }
        }
        return false;
    }

    public boolean checkForEach(ChessPiece piece, Coordinate toPosition, ChessBoard board) {
        return board.getSquareOfPiece(piece)
                .map(startingSquare -> piece.getEatMovements().stream()
                        .anyMatch(move -> move.checkMove(startingSquare, toPosition, board, piece.getColor()).outputResult()))
                .orElse(false);
    }
}
