package chess.logic.classicGame;

import common.logic.LegalMove;
import common.logic.PieceMover;
import common.logic.WinCondition;
import common.models.Board;
import common.models.Coordinate;
import common.models.SideColor;
import common.moves.Move;
import common.models.Piece;
import common.results.MoveResult;

import java.util.List;

public class ChessLegalMove implements LegalMove {
    private final PieceMover pieceMover = new PieceMover();
    private final CheckForCheck checkForCheck = new CheckForCheck();
    @Override
    public MoveResult<Board, Boolean, SideColor> movePiece(Piece piece, Coordinate toSquare, Board board, Coordinate initial, List<Move> moves, WinCondition winCondition) {
        MoveResult<Board, Boolean, SideColor> move = pieceMover.check(board, initial, toSquare, moves,piece, board.getPiece(toSquare).successfulResult().get());
        if (move.errorResult())
            return move;
        else {
            if (checkForCheck.check(move.successfulResult(),piece.getColor(),piece, toSquare))
                return new MoveResult<>(board, true,getOpositeColor(piece.getColor()), "Check Rule unfollowed");
            return winCondition.checkWin(board, piece, move, toSquare);
        }
    }
    private SideColor getOpositeColor(SideColor color) {
        if (color == SideColor.White)
            return SideColor.Black;
        if (color == SideColor.Black)
            return SideColor.White;
        return color;
    }
}