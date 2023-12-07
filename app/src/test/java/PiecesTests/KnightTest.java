package PiecesTests;
import checkers.logic.CheckersLegalMove;
import checkers.logic.CheckersWinCondition;
import chess.logic.classicGame.ChessLegalMove;
import chess.logic.classicGame.ClassicWinCondition;
import chess.logic.moves.HorizontalMove;
import chess.logic.moves.JumpMove;
import chess.logic.moves.VerticalMove;
import common.logic.PromotionAndCastlingCondition;
import common.models.*;
import common.moves.DiagonalMove;
import common.moves.Move;
import common.results.MoveResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class KnightTest {

    Game game;

    @BeforeEach
    public void setup() {
        List<PieceCoord> blackPieces = new ArrayList<>();
        List<PieceCoord> whitePieces = new ArrayList<>();
        PieceFactory pieceFactory = new PieceFactory();
        List<Move> knightMovements = new ArrayList<>();
        knightMovements.add(new JumpMove(2, 1));
        knightMovements.add(new JumpMove(2, -1));
        knightMovements.add(new JumpMove(-2, 1));
        knightMovements.add(new JumpMove(-2, -1));
        knightMovements.add(new JumpMove(1, 2));
        knightMovements.add(new JumpMove(1, -2));
        knightMovements.add(new JumpMove(-1, 2));
        knightMovements.add(new JumpMove(-1, -2));

        whitePieces.add(new PieceCoord(new Coordinate(4, 4),pieceFactory.createPiece("knight", knightMovements, false, SideColor.White)));
        whitePieces.add(new PieceCoord(new Coordinate(4, 5),pieceFactory.clonePiece("knight", SideColor.White)));
        whitePieces.add(new PieceCoord(new Coordinate(5, 5),pieceFactory.clonePiece("knight", SideColor.White)));
        blackPieces.add(new PieceCoord(new Coordinate(6, 6),pieceFactory.clonePiece("knight", SideColor.Black)));

        List<Move> kingMovements = new ArrayList<>();
        kingMovements.add(new VerticalMove(1, true));
        kingMovements.add(new HorizontalMove(1));
        kingMovements.add(new DiagonalMove(1, 1,true));
        kingMovements.add(new DiagonalMove(1, -1,true));
        whitePieces.add(new PieceCoord(new Coordinate(5, 1),pieceFactory.createPiece("king", kingMovements, true, SideColor.White)));
        blackPieces.add(new PieceCoord(new Coordinate(5, 8),pieceFactory.clonePiece("king", SideColor.Black)));


        Board board = new Board(8,8,blackPieces,whitePieces,pieceFactory);
        game = new Game(board, SideColor.Black, new ClassicWinCondition(), new ChessLegalMove(), new PromotionAndCastlingCondition());
    }

    @Test
    void testValidJumpMove() {
        MoveResult<Game, Boolean, SideColor> result = game.movePiece(new Coordinate(5, 8), new Coordinate(6, 8));
        assertEquals("Piece Moved", result.message());
    }

    @Test
    void testInvalidJumpMove() {
        MoveResult<Game, Boolean, SideColor> result = game.movePiece(new Coordinate(5, 8), new Coordinate(6, 6));
        assertEquals("Piece cannot move to the Square", result.message());
    }

}
