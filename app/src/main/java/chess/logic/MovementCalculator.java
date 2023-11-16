package chess.logic;

import common.models.Board;
import common.models.Coordinate;
import common.models.Piece;
import common.models.PieceName;
import common.moves.Move;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MovementCalculator {

    public List<Coordinate> calculatePossibleMovements(Board chessBoard, Piece chessPiece, Coordinate initialSquare) {
        List<Coordinate> possibleMoves = new ArrayList<>();

        for (Move move : chessPiece.getEatMovements()) {
            processMove(chessBoard, chessPiece, initialSquare, possibleMoves, move, true);
        }

        for (Move move : chessPiece.getMovements()) {
            processMove(chessBoard, chessPiece, initialSquare, possibleMoves, move, false);
        }

        return possibleMoves;
    }

    private void processMove(Board chessBoard, Piece chessPiece, Coordinate initialSquare, List<Coordinate> possibleMoves, Move move, boolean shouldEat) {
        for (int i = 1; i <= chessBoard.getColumns(); i++) {
            for (int j = 1; j <= chessBoard.getRows(); j++) {
                Coordinate finalSquare = new Coordinate(i, j);

                if (shouldEat) {
                    if (!Objects.equals(chessBoard.getSquare(finalSquare).getPiece().getName(), PieceName.NULL)
                            && CommonMoveValidator.isValidMove(chessBoard, chessPiece, finalSquare)
                            && move.checkMove(initialSquare, finalSquare, chessBoard, chessPiece.getColor()).outputResult()
                            && !checkDuplicated(possibleMoves, finalSquare)) {
                        possibleMoves.add(finalSquare);
                    }
                } else {
                    if (move.checkMove(initialSquare, finalSquare, chessBoard, chessPiece.getColor()).outputResult()
                            && CommonMoveValidator.isValidMove(chessBoard, chessPiece, finalSquare)
                            && !checkDuplicated(possibleMoves, finalSquare)) {
                        possibleMoves.add(finalSquare);
                    }
                }
            }
        }
    }

    private boolean checkDuplicated(List<Coordinate> possibleMoves, Coordinate finalSquare) {
        for (Coordinate possibleMove : possibleMoves) {
            if (possibleMove.equals(finalSquare)) {
                return true;
            }
        }
        return false;
    }
}