package common.logic;

import common.models.*;
import common.moves.Move;
import common.results.CheckResult;
import common.results.MoveResults;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class PieceMover {
    public MoveResults<Board, Boolean> check(Board board, Coordinate initial, Coordinate toSquare, List<Move> movements, Piece pieceMoving, Piece pieceEaten) {
        for (Move move : movements) {
            CheckResult<Coordinate, Boolean> checkResult = move.checkMove(initial, toSquare, board, pieceMoving.getColor());
            if (checkResult.outputResult()) {
                Optional<Coordinate> coordinateEaten = board.getSquareOfPiece(pieceEaten).successfulResult();
                Coordinate newSquare = checkResult.successfulResult();

                // Check if the moved piece is a pawn and has reached the border
                if (checkIfPawnReachedEnd(board, toSquare, pieceMoving)) {
                    // Promote the pawn (replace it with a promoted piece, e.g., Queen
                    pieceMoving = board.getPieceBuilder().clonePiece("queen", toSquare, pieceMoving.getColor(), pieceMoving.getId());
                }

                if (checkIfCastle(board, pieceMoving, initial, toSquare)){

                }

                // Update the board after the move
                Board b = board.positionPiece(board.getPieceBuilder().createNullPiece(coordinateEaten.get()), coordinateEaten.get());
                Board newBoard = b.positionPiece(pieceMoving, newSquare);
                Board newBoard2 = newBoard.positionPiece(board.getPieceBuilder().createNullPiece(initial), initial);

                // Update movement history
                List<MovementHistory> newMovement = new ArrayList<>(board.getMovements());
                MovementHistory movement = new MovementHistory(initial, newSquare, pieceMoving);
                newMovement.add(movement);

                // Create a new board with the updated state
                Board boa = new Board(board.getRows(), board.getColumns(), newBoard2.getPieces(), newBoard2.getSquares(), newMovement, board.getPieceBuilder());
                return new MoveResults<>(boa, false, "Piece Moved");
            }
        }
        return new MoveResults<>(board, true, "Piece not moved");
    }

    private boolean checkIfCastle(Board board, Piece pieceMoving, Coordinate initial, Coordinate toSquare) {
        if (Objects.equals(pieceMoving.getName(), "king") && checkIfFinalCoordinateIsMyRook(board, toSquare, pieceMoving.getColor()) ){
            return checkIfEmptyPath(board, initial, toSquare);
        }
        return false;
    }

    private boolean checkIfEmptyPath(Board board, Coordinate initial, Coordinate toSquare) {
        if (initial.row() != toSquare.row()) {
            return false;
        }

        int initialColumn = initial.column();
        int finalColumn = toSquare.column();

        // Adjust loop conditions to fix the off-by-one error
        if (initialColumn < finalColumn) {
            for (int i = initialColumn + 1; i < finalColumn; i++) {
                if (!Objects.equals(board.getSquare(new Coordinate(initial.row(), i)).getPiece().getName(), "null")) {
                    return false;
                }
            }
        } else {
            for (int i = finalColumn + 1; i < initialColumn; i++) {
                if (!Objects.equals(board.getSquare(new Coordinate(initial.row(), i)).getPiece().getName(), "null")) {
                    return false;
                }
            }
        }

        return true;
    }

    private boolean checkIfFinalCoordinateIsMyRook(Board board, Coordinate toSquare, SideColor color) {
        Piece checkPiece = board.getSquare(toSquare).getPiece();
        return (checkPiece.getColor() == color && Objects.equals(checkPiece.getName(), "rook"));
    }


    private boolean checkIfPawnReachedEnd(Board board, Coordinate toSquare, Piece piece) {
        return (Objects.equals(piece.getName(), "pawn")) && (toSquare.row() == 0 || toSquare.row() == board.getRows()) ;
    }
}
