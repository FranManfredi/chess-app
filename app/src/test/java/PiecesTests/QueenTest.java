package PiecesTests;

import chess.logic.classicGame.ChessLegalMove;
import chess.logic.classicGame.ClassicWinCondition;
import common.models.*;
import common.moves.DiagonalMove;
import common.moves.Move;
import common.results.MoveResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import chess.logic.moves.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
public class QueenTest {
    Game game;

    @BeforeEach
    public void setup() {
        List<Piece> blackPieces = new ArrayList<>();
        List<Piece> whitePieces = new ArrayList<>();
        PieceFactory pieceFactory = new PieceFactory();

        List<Move> queenMovements = new ArrayList<>();
        queenMovements.add(new VerticalMove(true));
        queenMovements.add(new HorizontalMove());
        queenMovements.add(new DiagonalMove());
        whitePieces.add(pieceFactory.createPiece("queen", new Coordinate(1, 2), queenMovements, false, SideColor.White));
        blackPieces.add(pieceFactory.clonePiece("queen", new Coordinate(1, 7), SideColor.Black));

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
    void testValidHorizontalMove() {
        MoveResult<Board, Boolean,SideColor> result = game.movePiece(new Coordinate(1, 2), new Coordinate(5, 2));
        assertEquals("Piece Moved", result.message());
    }

    @Test
    void testValidVerticalMove() {
        MoveResult<Board, Boolean,SideColor> result = game.movePiece(new Coordinate(1, 2), new Coordinate(1, 5));
        assertEquals("Piece Moved", result.message());
    }

    @Test
    void testValidDiagonalMove() {
        MoveResult<Board, Boolean,SideColor> result = game.movePiece(new Coordinate(1, 2), new Coordinate(5, 6));
        assertEquals("Piece Moved", result.message());
    }

    @Test
    void testInvalidMove() {
        MoveResult<Board, Boolean,SideColor> result = game.movePiece(new Coordinate(1, 2), new Coordinate(5, 5));
        assertEquals("Piece not moved", result.message());
    }
}
