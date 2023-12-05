package common.logic;

import common.models.Board;
import common.models.Coordinate;
import common.models.Piece;
import common.models.SideColor;
import common.moves.Move;
import common.results.MoveResult;

import java.util.List;

public interface LegalMove {
    MoveResult<Board,Boolean, SideColor> movePiece(Piece piece, Coordinate toSquare, Board board, Coordinate initial, List<Move> moves, WinCondition winCondition);
}
