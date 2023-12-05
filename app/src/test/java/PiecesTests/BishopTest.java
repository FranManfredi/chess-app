package PiecesTests;

import chess.logic.moves.HorizontalMove;
import chess.logic.moves.VerticalMove;
import common.models.*;
import common.moves.DiagonalMove;
import common.moves.Move;
import common.results.MoveResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BishopTest {

    Game game;

    @BeforeEach
    public void setup() {
        List<Piece> blackPieces = new ArrayList<>();
        List<Piece> whitePieces = new ArrayList<>();
        PieceFactory pieceFactory = new PieceFactory();

        List<Move> bishopMovements = new ArrayList<>();
        bishopMovements.add(new DiagonalMove());
        whitePieces.add(pieceFactory.createPiece("bishop", new Coordinate(1, 2), bishopMovements, false, SideColor.White));
        blackPieces.add(pieceFactory.clonePiece("bishop", new Coordinate(1, 7), SideColor.Black));

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
    void testValidDiagonalMove() {
        MoveResult<Board, Boolean,SideColor> result = game.movePiece(new Coordinate(1, 2), new Coordinate(5, 6));
        assertEquals("Piece Moved", result.message());
    }

    @Test
    void testInvalidDiagonalMove() {
        MoveResult<Board, Boolean,SideColor> result = game.movePiece(new Coordinate(1, 2), new Coordinate(5, 5));
        assertEquals("Piece not moved", result.message());
    }
}