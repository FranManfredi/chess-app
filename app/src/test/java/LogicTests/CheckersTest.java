package LogicTests;

import checkers.logic.CheckersEatMove;
import checkers.logic.CheckersLegalMove;
import checkers.logic.CheckersWinCondition;
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
    List<Piece> blackPieces = new ArrayList<>();
    List<Piece> whitePieces = new ArrayList<>();
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
        whitePieces.add(pieceFactory.createPiece("pawn", new Coordinate(3, 3), pawnMovements, pawnEatMovements, false, SideColor.White));
        blackPieces.add(pieceFactory.clonePiece("pawn", new Coordinate(4, 4), SideColor.Black));

        Board board = new Board(8,8,blackPieces,whitePieces,pieceFactory);
        Game game = new Game(new Player(), new Player(), board, SideColor.Black,new CheckersWinCondition(), new CheckersLegalMove());

        MoveResult<Board,Boolean,SideColor> result = game.movePiece(new Coordinate(4, 4), new Coordinate(3, 3));
        assertEquals("CheckMate", result.message());
    }

    @Test
    void TestCheckWinByNoMoreMoves() {
        whitePieces.add(pieceFactory.createPiece("queen", new Coordinate(1, 1), queenMovements, false, SideColor.White));

        blackPieces.add(pieceFactory.createPiece("pawn", new Coordinate(7, 8), pawnMovements, pawnEatMovements, false, SideColor.Black));
        whitePieces.add(pieceFactory.clonePiece("pawn", new Coordinate(6, 7), SideColor.White));
        whitePieces.add(pieceFactory.clonePiece("pawn", new Coordinate(7, 6), SideColor.White));
        whitePieces.add(pieceFactory.clonePiece("pawn", new Coordinate(5, 6), SideColor.White));
        whitePieces.add(pieceFactory.clonePiece("pawn", new Coordinate(6, 5), SideColor.White));

        Board board = new Board(8, 8, blackPieces, whitePieces, pieceFactory);
        Game game = new Game(new Player(), new Player(), board, SideColor.Black, new CheckersWinCondition(), new CheckersLegalMove());

        game.movePiece(new Coordinate(7, 8), new Coordinate(8, 7));
        MoveResult<Board, Boolean, SideColor> result = game.movePiece(new Coordinate(6, 7), new Coordinate(7, 8));

        assertEquals("CheckMate", result.message());
    }
}