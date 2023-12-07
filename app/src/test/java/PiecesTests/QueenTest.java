package PiecesTests;

import checkers.logic.CheckersLegalMove;
import checkers.logic.CheckersWinCondition;
import chess.logic.classicGame.ChessLegalMove;
import chess.logic.classicGame.ClassicWinCondition;
import common.logic.PromotionAndCastlingCondition;
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
        List<PieceCoord> blackPieces = new ArrayList<>();
        List<PieceCoord> whitePieces = new ArrayList<>();
        PieceFactory pieceFactory = new PieceFactory();

        List<Move> queenMovements = new ArrayList<>();
        queenMovements.add(new VerticalMove(true));
        queenMovements.add(new HorizontalMove());
        queenMovements.add(new DiagonalMove());
        whitePieces.add(new PieceCoord(new Coordinate(1, 2),pieceFactory.createPiece("queen", queenMovements, false, SideColor.White)));
        blackPieces.add(new PieceCoord(new Coordinate(1, 7),pieceFactory.clonePiece("queen", SideColor.Black)));

        List<Move> kingMovements = new ArrayList<>();
        kingMovements.add(new VerticalMove(1, true));
        kingMovements.add(new HorizontalMove(1));
        kingMovements.add(new DiagonalMove(1, 1,true));
        kingMovements.add(new DiagonalMove(1, -1,true));
        whitePieces.add(new PieceCoord(new Coordinate(5, 1),pieceFactory.createPiece("king", kingMovements, true, SideColor.White)));
        blackPieces.add(new PieceCoord(new Coordinate(5, 8),pieceFactory.clonePiece("king", SideColor.Black)));


        Board board = new Board(8,8,blackPieces,whitePieces,pieceFactory);
        game = new Game(board, SideColor.White, new ClassicWinCondition(), new ChessLegalMove(), new PromotionAndCastlingCondition());
    }

    @Test
    void testValidHorizontalMove() {
        MoveResult<Game, Boolean, SideColor> result = game.movePiece(new Coordinate(1, 2), new Coordinate(5, 2));
        assertEquals("Piece Moved", result.message());
    }

    @Test
    void testValidVerticalMove() {
        MoveResult<Game, Boolean, SideColor> result = game.movePiece(new Coordinate(1, 2), new Coordinate(1, 5));
        assertEquals("Piece Moved", result.message());
    }

    @Test
    void testValidDiagonalMove() {
        MoveResult<Game, Boolean, SideColor> result = game.movePiece(new Coordinate(1, 2), new Coordinate(5, 6));
        assertEquals("Piece Moved", result.message());
    }

    @Test
    void testInvalidMove() {
        MoveResult<Game, Boolean, SideColor> result = game.movePiece(new Coordinate(1, 2), new Coordinate(5, 5));
        assertEquals("Piece not moved", result.message());
    }
}
