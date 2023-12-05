package LogicTests;

import chess.logic.classicGame.ClassicGame;
import common.models.Board;
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
        game.movePiece(new Coordinate(6, 2), new Coordinate(6, 3));
        MoveResult<Board, Boolean, SideColor> move = game.movePiece(new Coordinate(5, 2), new Coordinate(5, 3));
        Assertions.assertEquals("Piece not same color as player", move.message());
    }
}
