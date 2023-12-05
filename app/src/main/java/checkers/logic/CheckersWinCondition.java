package checkers.logic;

import common.models.Board;
import common.models.Coordinate;
import common.models.Piece;
import common.logic.WinCondition;
import common.models.SideColor;
import common.results.MoveResult;

public class CheckersWinCondition implements WinCondition {
    private final CheckForWinByNoMorePieces CheckForWinByNoMorePieces = new CheckForWinByNoMorePieces();
    private final CheckForWinByNoMoreMoves CheckForWinByNoMoreMoves = new CheckForWinByNoMoreMoves();

    @Override
    public MoveResult<Board, Boolean, SideColor> checkWin(Board board, Piece piece, MoveResult<Board, Boolean,SideColor> move, Coordinate toSquare) {
        Board moveBoard = move.successfulResult();
        if (CheckForWinByNoMorePieces.check(moveBoard, piece.getColor())) {
            return new MoveResult<>(moveBoard, true,getOpositeColor(piece.getColor()), "CheckMate");
        }
        if (CheckForWinByNoMoreMoves.check(moveBoard, piece.getColor())) {
            return new MoveResult<>(moveBoard, true,getOpositeColor(piece.getColor()), "CheckMate");
        } else
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
