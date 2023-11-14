package chess.moves;

import common.models.ChessBoard;
import common.models.Coordinate;
import common.moves.Move;
import common.models.ChessPiece;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MovementCalculator {

    public List<Coordinate> calculatePossibleMovements(ChessBoard chessBoard, ChessPiece chessPiece, Coordinate initialSquare) {
        List<Coordinate> possibleMoves = new ArrayList<>();

        for (Move move : chessPiece.getEatMovements()) {
            processMove(chessBoard, chessPiece, initialSquare, possibleMoves, move, true);
        }

        for (Move move : chessPiece.getMovements()) {
            processMove(chessBoard, chessPiece, initialSquare, possibleMoves, move, false);
        }

        return possibleMoves;
    }

    private void processMove(ChessBoard chessBoard, ChessPiece chessPiece, Coordinate initialSquare, List<Coordinate> possibleMoves, Move move, boolean shouldEat) {
        for (int i = 1; i <= chessBoard.getColumns(); i++) {
            for (int j = 1; j <= chessBoard.getRows(); j++) {
                Coordinate finalSquare = new Coordinate(i, j);

                if (shouldEat) {
                    if (!Objects.equals(chessBoard.getSquare(finalSquare).getPiece().getName(), "null")
                            && CommonRule.checkRule(chessBoard, chessPiece, finalSquare)
                            && move.checkMove(initialSquare, finalSquare, chessBoard, chessPiece.getColor()).outputResult()
                            && !checkDuplicated(possibleMoves, finalSquare)) {
                        possibleMoves.add(finalSquare);
                    }
                } else {
                    if (move.checkMove(initialSquare, finalSquare, chessBoard, chessPiece.getColor()).outputResult()
                            && CommonRule.checkRule(chessBoard, chessPiece, finalSquare)
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