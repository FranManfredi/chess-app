package checkers.logic;

import common.models.*;
import common.moves.DiagonalMove;
import common.moves.Move;

import java.util.ArrayList;
import java.util.List;

public class CheckersGame {
    public Game CreateGame() {
        List<PieceCoord> blackPieces = new ArrayList<>();
        List<PieceCoord> whitePieces = new ArrayList<>();
        PieceFactory pieceFactory = new PieceFactory();

        createQueen(whitePieces, pieceFactory);
        createWhitePawns(whitePieces, pieceFactory);
        createBlackPawns(blackPieces, pieceFactory);



        Board board= new Board(8, 8,blackPieces,whitePieces, pieceFactory);
        return new Game(board, SideColor.Black, new CheckersWinCondition(), new CheckersLegalMove());
    }

    private void createBlackPawns(List<PieceCoord> blackPieces, PieceFactory pieceFactory) {
        blackPieces.add(new PieceCoord(new Coordinate(2, 6), pieceFactory.clonePiece("pawn", SideColor.Black)));
        blackPieces.add(new PieceCoord(new Coordinate(4, 6), pieceFactory.clonePiece("pawn", SideColor.Black)));
        blackPieces.add(new PieceCoord(new Coordinate(6, 6), pieceFactory.clonePiece("pawn", SideColor.Black)));
        blackPieces.add(new PieceCoord(new Coordinate(8, 6), pieceFactory.clonePiece("pawn", SideColor.Black)));
        blackPieces.add(new PieceCoord(new Coordinate(1, 7), pieceFactory.clonePiece("pawn", SideColor.Black)));
        blackPieces.add(new PieceCoord(new Coordinate(3, 7), pieceFactory.clonePiece("pawn", SideColor.Black)));
        blackPieces.add(new PieceCoord(new Coordinate(5, 7), pieceFactory.clonePiece("pawn", SideColor.Black)));
        blackPieces.add(new PieceCoord(new Coordinate(7, 7), pieceFactory.clonePiece("pawn", SideColor.Black)));
        blackPieces.add(new PieceCoord(new Coordinate(2, 8), pieceFactory.clonePiece("pawn", SideColor.Black)));
        blackPieces.add(new PieceCoord(new Coordinate(4, 8), pieceFactory.clonePiece("pawn", SideColor.Black)));
        blackPieces.add(new PieceCoord(new Coordinate(6, 8), pieceFactory.clonePiece("pawn", SideColor.Black)));
        blackPieces.add(new PieceCoord(new Coordinate(8, 8), pieceFactory.clonePiece("pawn", SideColor.Black)));
    }

    private void createWhitePawns(List<PieceCoord> whitePieces, PieceFactory pieceFactory) {
        List<Move> pawnMovements = new ArrayList<>();
        pawnMovements.add(new DiagonalMove(1, 1, false));
        pawnMovements.add(new DiagonalMove(1, -1, false));

        List<Move> pawnEatMovements = new ArrayList<>();
        pawnEatMovements.add(new CheckersEatMove(1, 1));
        pawnEatMovements.add(new CheckersEatMove(1, -1));

        whitePieces.add(new PieceCoord(new Coordinate(1, 1), pieceFactory.createPiece("pawn", pawnMovements, pawnEatMovements, true, SideColor.White)));
        whitePieces.add(new PieceCoord(new Coordinate(3, 1), pieceFactory.clonePiece("pawn", SideColor.White)));
        whitePieces.add(new PieceCoord(new Coordinate(5, 1), pieceFactory.clonePiece("pawn", SideColor.White)));
        whitePieces.add(new PieceCoord(new Coordinate(7, 1), pieceFactory.clonePiece("pawn", SideColor.White)));
        whitePieces.add(new PieceCoord(new Coordinate(2, 2), pieceFactory.clonePiece("pawn", SideColor.White)));
        whitePieces.add(new PieceCoord(new Coordinate(4, 2), pieceFactory.clonePiece("pawn", SideColor.White)));
        whitePieces.add(new PieceCoord(new Coordinate(6, 2), pieceFactory.clonePiece("pawn", SideColor.White)));
        whitePieces.add(new PieceCoord(new Coordinate(8, 2), pieceFactory.clonePiece("pawn", SideColor.White)));
        whitePieces.add(new PieceCoord(new Coordinate(1, 3), pieceFactory.clonePiece("pawn", SideColor.White)));
        whitePieces.add(new PieceCoord(new Coordinate(3, 3), pieceFactory.clonePiece("pawn", SideColor.White)));
        whitePieces.add(new PieceCoord(new Coordinate(5, 3), pieceFactory.clonePiece("pawn", SideColor.White)));
        whitePieces.add(new PieceCoord(new Coordinate(7, 3), pieceFactory.clonePiece("pawn", SideColor.White)));
    }

    private void createQueen(List<PieceCoord> whitePieces, PieceFactory pieceFactory) {
        List<Move> queenMovements = new ArrayList<>();
        queenMovements.add(new DiagonalMove());

        whitePieces.add(new PieceCoord(new Coordinate(1, 1), pieceFactory.createPiece("queen", queenMovements, false, SideColor.White)));
    }

}
