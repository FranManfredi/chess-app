package chess.logic;

import common.models.Board;
import common.models.Piece;
import common.models.PieceName;

import java.util.Objects;

public class CommonMoveValidator {

    public static boolean isValidMove(Board chessBoard, Piece chessPiece, common.models.Coordinate targetCoordinate) {
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

        common.models.Coordinate currentPieceCoordinate = chessBoard.getSquareOfPiece(chessPiece).successfulResult().get();

        if (targetColumn == currentPieceCoordinate.column() && targetRow == currentPieceCoordinate.row()) {
            return false;
        }

        Piece targetSquarePiece = chessBoard.getSquare(targetCoordinate).getPiece();
        return Objects.equals(targetSquarePiece.getName(), PieceName.NULL) || !Objects.equals(targetSquarePiece.getColor(), chessPiece.getColor());
    }
}
