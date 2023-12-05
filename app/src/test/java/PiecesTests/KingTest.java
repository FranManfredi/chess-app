package PiecesTests;

import chess.logic.classicGame.ChessLegalMove;
import chess.logic.classicGame.ClassicWinCondition;
import chess.logic.moves.HorizontalMove;
import chess.logic.moves.VerticalMove;
import common.models.*;
import common.moves.DiagonalMove;
import common.moves.Move;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import common.results.MoveResult;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.ArrayList;
import java.util.List;

public class KingTest {
    Game game;
    @BeforeEach
    void setup(){
        List<Piece> blackPieces = new ArrayList<>();
        List<Piece> whitePieces = new ArrayList<>();
        PieceFactory pieceFactory = new PieceFactory();
        List<Move> kingMovements = new ArrayList<>();
        kingMovements.add(new VerticalMove(1, true));
        kingMovements.add(new HorizontalMove(1));
        kingMovements.add(new DiagonalMove(1, 1,true));
        kingMovements.add(new DiagonalMove(1, -1,true));
        whitePieces.add(pieceFactory.createPiece("king", new Coordinate(5, 1), kingMovements, true, SideColor.White));
        blackPieces.add(pieceFactory.clonePiece("king", new Coordinate(5, 8), SideColor.Black));

        Board board = new Board(8,8,blackPieces,whitePieces,pieceFactory);
        game = new Game(new Player(), new Player(), board, SideColor.White,new ClassicWinCondition(), new ChessLegalMove());
    }

    @Test
    void testValidVerticalMove() {
        MoveResult<Board, Boolean,SideColor> result = game.movePiece(new Coordinate(5, 1), new Coordinate(5, 2));
        assertEquals("Piece Moved", result.message());
    }

    @Test
    void testValidHorizontalMove() {
        MoveResult<Board, Boolean,SideColor> result = game.movePiece(new Coordinate(5, 1), new Coordinate(6, 1));
        assertEquals("Piece Moved", result.message());
    }

    @Test
    void testValidDiagonalMove() {
        MoveResult<Board, Boolean,SideColor> result = game.movePiece(new Coordinate(5, 1), new Coordinate(6, 2));
        assertEquals("Piece Moved", result.message());
    }

    @Test
    void testInvalidMove() {
        MoveResult<Board, Boolean,SideColor> result = game.movePiece(new Coordinate(5, 1), new Coordinate(7, 2));
        assertEquals("Piece not moved", result.message());
    }
}
