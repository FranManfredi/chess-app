package LogicTests;

import chess.logic.classicGame.ClassicGame;
import common.models.Coordinate;
import common.models.Game;
import common.models.SideColor;
import common.results.MoveResult;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class TurnTests {
    @Test
    void testTurn() {
        ClassicGame cg = new ClassicGame();
        Game game = cg.CreateGame();
        MoveResult<Game, Boolean, SideColor> newGame = game.movePiece(new Coordinate(6, 2), new Coordinate(6, 3));
        MoveResult<Game, Boolean, SideColor> move = newGame.getBoard().movePiece(new Coordinate(5, 2), new Coordinate(5, 3));
        Assertions.assertEquals("Piece not found or not the correct color", move.message());
    }
}
