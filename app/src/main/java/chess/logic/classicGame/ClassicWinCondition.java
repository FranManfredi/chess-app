package chess.logic.classicGame;

import common.logic.WinCondition;
import common.models.Board;
import common.models.Coordinate;
import common.models.Piece;
import common.models.SideColor;
import common.results.MoveResult;


public class ClassicWinCondition implements WinCondition {
    private final CheckForCheckMate checkForCheckMate  = new CheckForCheckMate();
    @Override
    public MoveResult<Board,Boolean, SideColor> checkWin(Board board, Piece piece, MoveResult<Board,Boolean,SideColor> move, Coordinate toSquare) {
        Board moveBoard = move.successfulResult();
        if (checkForCheckMate.check(moveBoard,piece.getColor(),moveBoard.getPieces()))
            return new MoveResult<>(moveBoard, true,getOpositeColor(piece.getColor()), "CheckMate");
        else
            return move;
    }

    private SideColor getOpositeColor(SideColor color) {
        if (color == SideColor.White)
            return SideColor.Black;
        if (color == SideColor.Black)
            return SideColor.White;
        return color;
    }
}
