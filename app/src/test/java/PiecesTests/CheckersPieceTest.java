package PiecesTests;

import checkers.logic.CheckersGame;
import common.models.Board;
import common.models.Coordinate;
import common.models.Game;
import common.models.SideColor;
import common.results.MoveResult;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
public class CheckersPieceTest {
    @Test
    void canMoveDiagonally() {
        CheckersGame cg = new CheckersGame();
        Game game = cg.CreateGame();
        MoveResult<Game, Boolean, SideColor> move = game.movePiece(new Coordinate(4, 6), new Coordinate(3, 5));
        assertEquals("Piece Moved", move.message());
    }

    @Test
    void cantMoveHorizontal(){
        CheckersGame cg = new CheckersGame();
        Game game = cg.CreateGame();
        MoveResult<Game, Boolean, SideColor> move = game.movePiece(new Coordinate(4, 6), new Coordinate(4, 5));
        assertEquals("Piece not moved", move.message());
    }

    @Test
    void cantMoveVertical(){
        CheckersGame cg = new CheckersGame();
        Game game = cg.CreateGame();
        MoveResult<Game, Boolean, SideColor> move = game.movePiece(new Coordinate(4, 6), new Coordinate(3, 6));
        assertEquals("Piece not moved", move.message());
    }

}
