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
        MoveResult<Board,Boolean, SideColor> move = game.movePiece(new Coordinate(4, 6), new Coordinate(3, 5));
        assertEquals("Piece Moved", move.message());
    }

    @Test
    void cantMoveHorizontal(){
        CheckersGame cg = new CheckersGame();
        Game game = cg.CreateGame();
        MoveResult<Board,Boolean, SideColor> move = game.movePiece(new Coordinate(4, 6), new Coordinate(4, 5));
        assertEquals("Piece not moved", move.message());
    }

    @Test
    void cantMoveVertical(){
        CheckersGame cg = new CheckersGame();
        Game game = cg.CreateGame();
        MoveResult<Board,Boolean, SideColor> move = game.movePiece(new Coordinate(4, 6), new Coordinate(3, 6));
        assertEquals("Piece not moved", move.message());
    }

    @Test
    void canEatDiagonally(){
        CheckersGame cg = new CheckersGame();
        Game game = cg.CreateGame();
        game.movePiece(new Coordinate(4, 6), new Coordinate(3, 5));
        game.movePiece(new Coordinate(1, 3), new Coordinate(2, 4));
        MoveResult<Board,Boolean, SideColor> move = game.movePiece(new Coordinate(3, 5), new Coordinate(2, 4));
        assertEquals("Piece Moved", move.message());
    }
}
