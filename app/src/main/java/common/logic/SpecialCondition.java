package common.logic;

import common.models.Board;
import common.models.Coordinate;
import common.models.Piece;
import common.models.SideColor;
import common.results.MoveResult;

public interface SpecialCondition {
    Board getBoard(Board board, Piece piece, Coordinate toSquare);
    boolean checkCondition(Board board, Piece piece, Coordinate toSquare);
    boolean overrideCommonRule(Board board, Piece piece, Coordinate toSquare);
}
