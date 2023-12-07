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
                                                       List<Move> movements, Piece pieceMoving, Piece pieceEaten,
                                                       SpecialCondition specialConditions) {
        if (specialConditions.overrideCommonRule(board, pieceMoving, toSquare)) {
            return processSpecialMove(board, initial, pieceMoving, specialConditions, toSquare);
        }
        for (Move move : movements) {
            CheckResult<Coordinate, Boolean> checkResult = move.checkMove(initial, toSquare, board, pieceMoving.getColor());
            if (checkIfEatPawn( pieceMoving, toSquare, board, move)) {
                return new MoveResult<>(board, true, pieceMoving.getColor(), "Piece not moved");
            }
            else if (checkResult.outputResult()) {
                return processSuccessfulMove(board, initial, checkResult, pieceMoving, pieceEaten, specialConditions);
            }
        }

        return new MoveResult<>(board, true, pieceMoving.getColor(), "Piece not moved");
    }

    private MoveResult<Board, Boolean, SideColor> processSpecialMove(Board board, Coordinate initial, Piece pieceMoving, SpecialCondition specialConditions, Coordinate toSquare) {
        Board newBoard = specialConditions.getBoard(board, pieceMoving, toSquare);
        List<MovementHistory> newMovement = new ArrayList<>(board.getMovements());
        newMovement.add(new MovementHistory(initial, board.getCoordOfPiece(pieceMoving).successfulResult().get(), pieceMoving));


        Board updatedBoard = new Board(board.getRows(), board.getColumns(), newBoard.getPieces(),
                newBoard.getSquares(), newMovement, board.getPieceBuilder());

        return new MoveResult<>(updatedBoard, false, checkOppositeColor(pieceMoving), "Piece Moved");
    }

    private boolean checkIfEatPawn(Piece pieceMoving, Coordinate toSquare, Board board, Move move) {
        return pieceMoving.getEatMovements().contains(move) && !board.checkForPieceInSquare(toSquare) && pieceMoving.getName().equals("pawn");
    }

    private MoveResult<Board, Boolean, SideColor> processSuccessfulMove(Board board, Coordinate initial,
                                                                        CheckResult<Coordinate, Boolean> checkResult,
                                                                        Piece pieceMoving, Piece pieceEaten, SpecialCondition specialConditions) {
        Optional<Coordinate> coordinateEaten = board.getCoordOfPiece(pieceEaten).successfulResult();
        Board newBoard = board.positionPiece(board.getPieceBuilder().createNullPiece(), coordinateEaten.get());
        newBoard = newBoard.positionPiece(pieceMoving, checkResult.successfulResult());
        newBoard = newBoard.positionPiece(board.getPieceBuilder().createNullPiece(), initial);

        if (specialConditions.checkCondition(board, pieceMoving, checkResult.successfulResult())) {
            newBoard = specialConditions.getBoard(newBoard, pieceMoving, checkResult.successfulResult());
        }

        List<MovementHistory> newMovement = new ArrayList<>(board.getMovements());
        newMovement.add(new MovementHistory(initial, checkResult.successfulResult(), pieceMoving));

        Board updatedBoard = new Board(board.getRows(), board.getColumns(), newBoard.getPieces(),
                newBoard.getSquares(), newMovement, board.getPieceBuilder());

        return new MoveResult<>(updatedBoard, false, checkOppositeColor(pieceMoving), "Piece Moved");
    }


    private SideColor checkOppositeColor(Piece pieceMoving) {
        // Determine the opposite color based on the current piece's color
        return pieceMoving.getColor().equals(SideColor.Black) ? SideColor.White : SideColor.Black;
    }
}
