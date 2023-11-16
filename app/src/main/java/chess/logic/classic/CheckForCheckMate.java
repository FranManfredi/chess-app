package chess.logic.classic;


import common.models.Board;
import common.models.Coordinate;
import common.models.Piece;
import common.models.SideColor;

import java.util.List;

import chess.logic.MoveValidator;
import chess.logic.MovementCalculator;

public class CheckForCheckMate {
        private final MoveValidator moveValidation = new MoveValidator();
        private final MovementCalculator possibleMovements = new MovementCalculator();
        public Boolean check(Board board, SideColor color, List<Piece> pieces, CheckForCheck checkForCheck){
            SideColor oppositeColor = color == SideColor.White ? SideColor.Black : SideColor.White;
            for (Piece piece : pieces) {
                if (piece.getColor() == oppositeColor) {
                    if (board.getSquareOfPiece(piece).successfulResult().isEmpty()) {
                        continue;
                    }
                    Coordinate initialSquare = board.getSquareOfPiece(piece).successfulResult().get();
                    List<Coordinate> possibleMoves = possibleMovements.calculatePossibleMovements(board,piece, initialSquare);
                    for (Coordinate possibleMove : possibleMoves) {
                        if(!moveValidation.isValidMove(piece, board,possibleMove, initialSquare)){
                            continue;
                        }
                        Board newBoard = board.positionPiece(piece, possibleMove);
                        if(!checkForCheck.check(newBoard,oppositeColor, piece, possibleMove)){
                            return false;
                        }
                    }
                }
            }
            return true;
        }
}