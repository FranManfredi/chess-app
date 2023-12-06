package LogicTests;

import checkers.logic.CheckersEatMove;
import checkers.logic.CheckersLegalMove;
import checkers.logic.CheckersWinCondition;
import common.logic.PromotionCondition;
import common.models.*;
import common.moves.DiagonalMove;
import common.moves.Move;
import common.results.MoveResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CheckersTest {
    List<PieceCoord> blackPieces = new ArrayList<>();
    List<PieceCoord> whitePieces = new ArrayList<>();
    PieceFactory pieceFactory = new PieceFactory();
    List<Move> pawnMovements = new ArrayList<>();
    List<Move> queenMovements = new ArrayList<>();
    List<Move> pawnEatMovements = new ArrayList<>();
    @BeforeEach
    void setup(){
        pawnMovements.add(new DiagonalMove(1, 1, false));
        pawnMovements.add(new DiagonalMove(1, -1, false));

        pawnEatMovements.add(new CheckersEatMove(1, 1));
        pawnEatMovements.add(new CheckersEatMove(1, -1));

        queenMovements.add(new DiagonalMove());
    }

    @Test
    void TestCheckWinByNoMorePiece() {
        whitePieces.add(new PieceCoord(new Coordinate(3, 3),pieceFactory.createPiece("pawn", pawnMovements, pawnEatMovements, false, SideColor.White)));
        blackPieces.add(new PieceCoord( new Coordinate(4, 4),pieceFactory.clonePiece("pawn", SideColor.Black)));

        Board board = new Board(8,8,blackPieces,whitePieces,pieceFactory);
        Game game = new Game(board, SideColor.Black, new CheckersWinCondition(), new CheckersLegalMove(), new PromotionCondition());

        MoveResult<Game, Boolean, SideColor> result = game.movePiece(new Coordinate(4, 4), new Coordinate(3, 3));
        assertEquals("CheckMate", result.message());
    }

    @Test
    void TestCheckWinByNoMoreMoves() {
        whitePieces.add(new PieceCoord( new Coordinate(1, 1),pieceFactory.createPiece("queen", queenMovements, false, SideColor.White)));

        blackPieces.add(new PieceCoord( new Coordinate(7, 8),pieceFactory.createPiece("pawn", pawnMovements, pawnEatMovements, false, SideColor.Black)));

        whitePieces.add(new PieceCoord( new Coordinate(6, 7),pieceFactory.clonePiece("pawn", SideColor.White)));
        whitePieces.add(new PieceCoord( new Coordinate(7, 6),pieceFactory.clonePiece("pawn", SideColor.White)));
        whitePieces.add(new PieceCoord( new Coordinate(5, 6),pieceFactory.clonePiece("pawn", SideColor.White)));
        whitePieces.add(new PieceCoord( new Coordinate(6, 5),pieceFactory.clonePiece("pawn", SideColor.White)));

        Board board = new Board(8, 8, blackPieces, whitePieces, pieceFactory);
        Game game = new Game(board, SideColor.Black, new CheckersWinCondition(), new CheckersLegalMove(), new PromotionCondition());

        game.movePiece(new Coordinate(7, 8), new Coordinate(8, 7));
        MoveResult<Game, Boolean, SideColor> result = game.movePiece(new Coordinate(6, 7), new Coordinate(7, 8));

        assertEquals("CheckMate", result.message());
    }
}
