package chess.game.classic;

import common.models.ChessBoard;
import common.models.Coordinate;
import common.models.ChessPiece;
import common.models.SideColor;

import java.util.List;

public class CheckForCheckMate {

    private final MoveValidation moveValidation = new MoveValidation();
    private final PossibleMovements possibleMovements = new PossibleMovements();

    public boolean checkMate(ChessBoard board, SideColor color, List<ChessPiece> pieces, CheckForCheck checkForCheck) {
        SideColor oppositeColor = (color == SideColor.White) ? SideColor.Black : SideColor.White;

        for (ChessPiece piece : pieces) {
            if (piece.getColor() == oppositeColor && board.getSquareOfPiece(piece).isPresent()) {
                Coordinate initialSquare = board.getSquareOfPiece(piece).get();
                List<Coordinate> possibleMoves = possibleMovements.getPossibleMovements(board, piece, initialSquare);

                for (Coordinate possibleMove : possibleMoves) {
                    if (!moveValidation.validateMove(piece, board, possibleMove, initialSquare)) {
                        continue;
                    }

                    ChessBoard newBoard = board.positionPiece(piece, possibleMove);

                    if (!checkForCheck.check(newBoard, oppositeColor, piece, possibleMove)) {
                        return false;
                    }
                }
            }
        }

        return true;
    }
}