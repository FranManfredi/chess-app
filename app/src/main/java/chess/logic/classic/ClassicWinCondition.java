package chess.logic.classic;

import common.logic.WinCondition;
import common.models.Board;
import common.models.Coordinate;
import common.models.Piece;
import common.results.MoveResults;

public class ClassicWinCondition implements WinCondition {

    private final CheckForCheck checkForCheck = new CheckForCheck();
    private final CheckForCheckMate checkForCheckMate = new CheckForCheckMate();

    @Override
    public MoveResults<Board, Boolean> checkWin(Board board, Piece piece, MoveResults<Board, Boolean> move, Coordinate toSquare) {
        Board moveBoard = move.successfulResult();

        if (checkForCheckMate.check(moveBoard, piece.getColor(), moveBoard.getPieces(), checkForCheck))
            return new MoveResults<>(moveBoard, true, "Checkmate");

        if (checkForCheck.check(moveBoard, piece.getColor(), piece, toSquare))
            return new MoveResults<>(board, true, "Check Rule unfollowed");
        else
            return move;
    }
}