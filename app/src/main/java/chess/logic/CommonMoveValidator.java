package chess.rules;

import chess.models.Coordinate;
import chess.models.ChessBoard;
import chess.models.ChessPiece;

import java.util.Objects;

public class CommonMoveValidator {

    public static boolean isValidMove(ChessBoard chessBoard, ChessPiece chessPiece, Coordinate targetCoordinate) {
        if (chessBoard.getSquareOfPiece(chessPiece) == null) {
            return false;
        }

        int boardColumns = chessBoard.getColumns();
        int boardRows = chessBoard.getRows();

        int targetColumn = targetCoordinate.column();
        int targetRow = targetCoordinate.row();

        if (targetColumn > boardColumns || targetRow > boardRows || targetColumn <= 0 || targetRow <= 0) {
            return false;
        }

        Coordinate currentPieceCoordinate = chessBoard.getSquareOfPiece(chessPiece).successfulResult().get();

        if (targetColumn == currentPieceCoordinate.column() && targetRow == currentPieceCoordinate.row()) {
            return false;
        }

        ChessPiece targetSquarePiece = chessBoard.getSquare(targetCoordinate).getPiece();
        return Objects.equals(targetSquarePiece.getName(), "null") || !Objects.equals(targetSquarePiece.getColor(), chessPiece.getColor());
    }
}
