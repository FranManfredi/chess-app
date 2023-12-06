package common.logic;

import common.models.*;
import common.moves.Move;
import common.results.CheckResult;
import common.results.MoveResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PieceMover {

    public MoveResult<Board, Boolean, SideColor> check(Board board, Coordinate initial, Coordinate toSquare,
                                                       List<Move> movements, Piece pieceMoving, Piece pieceEaten) {
        for (Move move : movements) {
            CheckResult<Coordinate, Boolean> checkResult = move.checkMove(initial, toSquare, board, pieceMoving.getColor());

            if (checkResult.outputResult()) {
                return processSuccessfulMove(board, initial, checkResult, pieceMoving, pieceEaten);
            }
        }

        return new MoveResult<>(board, true, pieceMoving.getColor(), "Piece not moved");
    }

    private MoveResult<Board, Boolean, SideColor> processSuccessfulMove(Board board, Coordinate initial,
                                                                        CheckResult<Coordinate, Boolean> checkResult,
                                                                        Piece pieceMoving, Piece pieceEaten) {
        Optional<Coordinate> coordinateEaten = board.getCoordOfPiece(pieceEaten).successfulResult();
        Board newBoard = board.positionPiece(board.getPieceBuilder().createNullPiece(), coordinateEaten.get());
        newBoard = newBoard.positionPiece(pieceMoving, checkResult.successfulResult());
        newBoard = newBoard.positionPiece(board.getPieceBuilder().createNullPiece(), initial);

        if (pawnReachedEnd(pieceMoving, checkResult.successfulResult(), board)) {
            newBoard = newBoard.positionPiece(board.getPieceBuilder().promotePawn(pieceMoving.getColor(), pieceMoving.getId()), checkResult.successfulResult());
        }

        List<MovementHistory> newMovement = new ArrayList<>(board.getMovements());
        newMovement.add(new MovementHistory(initial, checkResult.successfulResult(), pieceMoving));

        Board updatedBoard = new Board(board.getRows(), board.getColumns(), newBoard.getPieces(),
                newBoard.getSquares(), newMovement, board.getPieceBuilder());

        return new MoveResult<>(updatedBoard, false, checkOppositeColor(pieceMoving), "Piece Moved");
    }


    private SideColor checkOppositeColor(Piece pieceMoving) {
        // Determine the opposite color based on the current piece's color
        return (pieceMoving.getColor().equals(SideColor.Black)) ? SideColor.White : SideColor.Black;
    }

    private boolean pawnReachedEnd(Piece pieceMoving, Coordinate coordinate, Board board) {
        // Check if a pawn reached the end of the board
        return pieceMoving.getName().equals("pawn") && (coordinate.row() == 1 || coordinate.row() == board.getRows());
    }
}
