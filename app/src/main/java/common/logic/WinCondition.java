package common.logic;

import common.models.Board;
import common.models.Coordinate;
import common.models.Piece;
import common.models.SideColor;
import common.results.MoveResult;

public interface WinCondition {
    MoveResult<Board,Boolean, SideColor> checkWin(Board board, Piece piece, MoveResult<Board,Boolean,SideColor> move, Coordinate toSquare);
}
