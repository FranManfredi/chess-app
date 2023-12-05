package LogicTests;

import chess.logic.classicGame.ClassicGame;
import common.models.Board;
import common.models.Coordinate;
import common.models.Game;
import common.models.SideColor;
import common.results.MoveResult;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class ChessTest {

    @Test
    void TestCheckMate() {
        ClassicGame cg = new ClassicGame();
        Game game = cg.CreateGame();
        game.movePiece(new Coordinate(6, 2), new Coordinate(6, 3));
        game.movePiece(new Coordinate(5, 7), new Coordinate(5, 6));
        game.movePiece(new Coordinate(7, 2), new Coordinate(7, 4));
        MoveResult<Board, Boolean, SideColor> g = game.movePiece(new Coordinate(4, 8), new Coordinate(8, 4));

        assertEquals("CheckMate", g.message());
    }
    @Test
    void TestIfPieceCanMoveOutsideBoard() {
        ClassicGame cg = new ClassicGame();
        Game game = cg.CreateGame();
        MoveResult<Board, Boolean,SideColor> g = game.movePiece(new Coordinate(1, 2), new Coordinate(1, 9));
        assertEquals("Common Rule unfollowed", g.message());
    }

    @Test
    void TestIfPieceCanMoveToSameSquare() {
        ClassicGame cg = new ClassicGame();
        Game game = cg.CreateGame();
        MoveResult<Board, Boolean,SideColor> g = game.movePiece(new Coordinate(1, 2), new Coordinate(1, 2));
        assertEquals("Common Rule unfollowed", g.message());
    }

}
