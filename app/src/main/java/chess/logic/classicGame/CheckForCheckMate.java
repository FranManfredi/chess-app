package chess.logic.classicGame;

import common.logic.MoveValidation;
import common.logic.PossibleMovements;
import common.models.*;

import java.util.List;

public class CheckForCheckMate {
        private final MoveValidation moveValidation = new MoveValidation();
        private final PossibleMovements possibleMovements = new PossibleMovements();
        private final CheckForCheck checkForCheck = new CheckForCheck();
        public Boolean check(Board board, SideColor color, List<PieceCoord> pieces){
            SideColor oppositeColor = color == SideColor.White ? SideColor.Black : SideColor.White;
            for (PieceCoord pieceCoord : pieces) {
                if (checkEachValidPossibleMove(board, pieceCoord.piece(), oppositeColor))
                    return false;
            }
            return true;
        }

    private boolean theresNoInitialPiece(Board board, Piece piece) {
        return board.getCoordOfPiece(piece).successfulResult().isEmpty();
    }

    private boolean checkEachValidPossibleMove(Board board, Piece piece, SideColor oppositeColor) {
        if (piece.getColor() == oppositeColor && !theresNoInitialPiece(board, piece)) {
            Coordinate initialSquare = board.getCoordOfPiece(piece).successfulResult().get();
            List<Coordinate> possibleMoves = possibleMovements.getPossibleMovements(board, piece, initialSquare);
            return checkEachValidPossibleMove(board, piece, possibleMoves, initialSquare, oppositeColor);
        }
        return false;
    }

    private boolean checkEachValidPossibleMove(Board board, Piece piece, List<Coordinate> possibleMoves, Coordinate initialSquare, SideColor oppositeColor) {
        for (Coordinate possibleMove : possibleMoves) {
            if(!moveValidation.validateMove(piece, board,possibleMove, initialSquare)){
                continue;
            }
            Board newBoard = board.positionPiece(piece, possibleMove);
            if(!checkForCheck.check(newBoard, oppositeColor, piece, possibleMove)){
                return true;
            }
        }
        return false;
    }
}
