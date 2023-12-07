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
    void TestIfPieceCanMoveOutsideBoard() {
        ClassicGame cg = new ClassicGame();
        Game game = cg.CreateGame();
        MoveResult<Game, Boolean, SideColor> g = game.movePiece(new Coordinate(1, 2), new Coordinate(1, 9));
        assertEquals("Piece cannot move to the Square", g.message());
    }

    @Test
    void TestIfPieceCanMoveToSameSquare() {
        ClassicGame cg = new ClassicGame();
        Game game = cg.CreateGame();
        MoveResult<Game, Boolean, SideColor> g = game.movePiece(new Coordinate(1, 2), new Coordinate(1, 2));
        assertEquals("Piece cannot move to the Square", g.message());
    }

}
