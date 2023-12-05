package chess.logic.capablancaGame;

import common.logic.WinCondition;
import common.models.Board;
import common.models.Coordinate;
import common.models.Piece;
import common.models.SideColor;
import common.results.MoveResult;

import java.util.Objects;

public class FirstToEat implements WinCondition {

    @Override
    public MoveResult<Board, Boolean, SideColor> checkWin(Board board, Piece piece, MoveResult<Board, Boolean, SideColor> move, Coordinate toSquare) {
        if (!Objects.equals(board.getPiece(toSquare).successfulResult().get().getName(), "null")) {
            return new MoveResult<>(move.successfulResult(), true,getOpositeColor(piece.getColor()), "CheckMate");
        }
        else return move;
    }

    private SideColor getOpositeColor(SideColor color) {
        if (color == SideColor.White)
            return SideColor.Black;
        else
            return SideColor.White;
    }
}
