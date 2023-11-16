package chess.logic.classic;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import common.models.Board;
import common.models.Coordinate;
import common.models.MovementHistory;
import common.models.Piece;
import common.models.PieceBuilder;
import common.moves.Move;
import common.results.CheckResult;
import common.results.MoveResults;

public class PieceMover {

    private final PieceBuilder pieceBuilder = new PieceBuilder();

    public MoveResults<Board, Boolean> check(Board board, Coordinate initial, Coordinate toSquare, List<Move> movements, Piece pieceMoving, Piece pieceEaten) {
        for (Move move : movements) {
            CheckResult<Coordinate, Boolean> checkResult = move.checkMove(initial, toSquare, board, pieceMoving.getColor());

            if (checkResult.outputResult()) {
                Optional<Coordinate> coordinateEaten = board.getSquareOfPiece(pieceEaten).successfulResult();
                Board tempBoard = board.positionPiece(pieceBuilder.createNullPiece(coordinateEaten.get()), coordinateEaten.get());
                Board newBoard = tempBoard.positionPiece(pieceMoving, checkResult.successfulResult());
                Board finalBoard = newBoard.positionPiece(pieceBuilder.createNullPiece(initial), initial);

                List<MovementHistory> newMovement = new ArrayList<>(board.getMovements());
                MovementHistory movement = new MovementHistory(initial, checkResult.successfulResult(), pieceMoving);
                newMovement.add(movement);

                Board updatedBoard = new Board(board.getRows(), board.getColumns(), finalBoard.getPieces(), finalBoard.getSquares(), newMovement);
                return new MoveResults<>(updatedBoard, false, "Piece Moved");
            }
        }
        return new MoveResults<>(board, true, "Piece not moved");
    }
}