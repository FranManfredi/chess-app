package chess.logic.capablancaGame;

import chess.logic.classicGame.ChessLegalMove;
import chess.logic.classicGame.ClassicWinCondition;
import chess.logic.moves.HorizontalMove;
import chess.logic.moves.InitialVerticalMove;
import chess.logic.moves.JumpMove;
import chess.logic.moves.VerticalMove;
import common.logic.promotionCondition;
import common.models.*;
import common.moves.DiagonalMove;
import common.moves.Move;

import java.util.ArrayList;
import java.util.List;

public class CapablancaChess {
    public Game CreateGame() {
        List<PieceCoord> blackPieces = new ArrayList<>();
        List<PieceCoord> whitePieces = new ArrayList<>();
        PieceFactory pieceFactory = new PieceFactory();

        createWhitePawns(whitePieces, pieceFactory,10);
        createWhiteRooks(whitePieces, pieceFactory);
        createWhiteBishops(whitePieces, pieceFactory);
        createWhiteKnights(whitePieces, pieceFactory);
        createWhiteKingAndQueen(whitePieces, pieceFactory);
        createWhiteChancellor(whitePieces, pieceFactory);
        createWhiteArchbishop(whitePieces, pieceFactory);


        createBlackPawns(blackPieces, pieceFactory,10);
        createBlackRooks(blackPieces, pieceFactory);
        createBlackBishops(blackPieces, pieceFactory);
        createBlackKnights(blackPieces, pieceFactory);
        createBlackKingAndQueen(blackPieces, pieceFactory);
        CreateBlackSpecialPieces(blackPieces, pieceFactory);


        Board board = new Board(8, 10, blackPieces, whitePieces, pieceFactory);
        return new Game(board,SideColor.White, new ClassicWinCondition(), new ChessLegalMove(), new promotionCondition());
    }

    private void createBlackKingAndQueen(List<PieceCoord> blackPieces, PieceFactory pieceFactory) {
        blackPieces.add(new PieceCoord(new Coordinate(5, 8), pieceFactory.clonePiece("queen", SideColor.Black)));
        blackPieces.add(new PieceCoord(new Coordinate(6, 8), pieceFactory.clonePiece("king", SideColor.Black)));
    }

    private void createBlackKnights(List<PieceCoord> blackPieces, PieceFactory pieceFactory) {
        blackPieces.add(new PieceCoord(new Coordinate(2, 8), pieceFactory.clonePiece("knight", SideColor.Black)));
        blackPieces.add(new PieceCoord(new Coordinate(9, 8), pieceFactory.clonePiece("knight", SideColor.Black)));
    }

    private void createBlackBishops(List<PieceCoord> blackPieces, PieceFactory pieceFactory) {
        blackPieces.add(new PieceCoord(new Coordinate(4, 8), pieceFactory.clonePiece("bishop", SideColor.Black)));
        blackPieces.add(new PieceCoord(new Coordinate(7, 8), pieceFactory.clonePiece("bishop", SideColor.Black)));
    }

    private void createBlackRooks(List<PieceCoord> blackPieces, PieceFactory pieceFactory) {
        blackPieces.add(new PieceCoord(new Coordinate(1, 8), pieceFactory.clonePiece("rook", SideColor.Black)));
        blackPieces.add(new PieceCoord(new Coordinate(10, 8), pieceFactory.clonePiece("rook", SideColor.Black)));
    }

    private void CreateBlackSpecialPieces(List<PieceCoord> blackPieces, PieceFactory pieceFactory) {
        blackPieces.add(new PieceCoord(new Coordinate(3, 8), pieceFactory.clonePiece("chancellor", SideColor.Black)));
        blackPieces.add(new PieceCoord(new Coordinate(8, 8), pieceFactory.clonePiece("archbishop", SideColor.Black)));
    }

    private void createBlackPawns(List<PieceCoord> blackPieces, PieceFactory pieceFactory, Integer quantity) {
        for (int i = 1; i <= quantity; i++) {
            blackPieces.add(new PieceCoord(new Coordinate(i, 7), pieceFactory.clonePiece("pawn", SideColor.Black)));
        }
    }

    private void createWhiteChancellor(List<PieceCoord> whitePieces, PieceFactory pieceFactory) {
        List<Move> chancellorMovements = new ArrayList<>();
        chancellorMovements.add(new JumpMove(2, 1));
        chancellorMovements.add(new JumpMove(2, -1));
        chancellorMovements.add(new JumpMove(-2, 1));
        chancellorMovements.add(new JumpMove(-2, -1));
        chancellorMovements.add(new JumpMove(1, 2));
        chancellorMovements.add(new JumpMove(1, -2));
        chancellorMovements.add(new JumpMove(-1, 2));
        chancellorMovements.add(new JumpMove(-1, -2));
        chancellorMovements.add(new VerticalMove(true));
        chancellorMovements.add(new HorizontalMove());

        whitePieces.add(new PieceCoord(new Coordinate(3, 1), pieceFactory.createPiece("chancellor", chancellorMovements, false, SideColor.White)));
    }

    private void createWhiteArchbishop(List<PieceCoord> whitePieces, PieceFactory pieceFactory) {
        List<Move> archBishopMovements = new ArrayList<>();
        archBishopMovements.add(new JumpMove(2, 1));
        archBishopMovements.add(new JumpMove(2, -1));
        archBishopMovements.add(new JumpMove(-2, 1));
        archBishopMovements.add(new JumpMove(-2, -1));
        archBishopMovements.add(new JumpMove(1, 2));
        archBishopMovements.add(new JumpMove(1, -2));
        archBishopMovements.add(new JumpMove(-1, 2));
        archBishopMovements.add(new JumpMove(-1, -2));
        archBishopMovements.add(new DiagonalMove());

        whitePieces.add(new PieceCoord(new Coordinate(8, 1), pieceFactory.createPiece("archbishop", archBishopMovements, false, SideColor.White)));
    }

    private void createWhiteKingAndQueen(List<PieceCoord> whitePieces, PieceFactory pieceFactory) {
        List<Move> kingMovements = new ArrayList<>();
        kingMovements.add(new VerticalMove(1, true));
        kingMovements.add(new HorizontalMove(1));
        kingMovements.add(new DiagonalMove(1, 1,false));
        kingMovements.add(new DiagonalMove(1, -1,true));

        List<Move> queenMovements = new ArrayList<>();
        queenMovements.add(new VerticalMove(true));
        queenMovements.add(new HorizontalMove());
        queenMovements.add(new DiagonalMove());

        whitePieces.add(new PieceCoord(new Coordinate(5, 1), pieceFactory.createPiece("queen", queenMovements, false, SideColor.White)));
        whitePieces.add(new PieceCoord(new Coordinate(6, 1), pieceFactory.createPiece("king", kingMovements, true, SideColor.White)));
    }

    private void createWhiteKnights(List<PieceCoord> whitePieces, PieceFactory pieceFactory) {
        List<Move> knightMovements = new ArrayList<>();
        knightMovements.add(new JumpMove(2, 1));
        knightMovements.add(new JumpMove(2, -1));
        knightMovements.add(new JumpMove(-2, 1));
        knightMovements.add(new JumpMove(-2, -1));
        knightMovements.add(new JumpMove(1, 2));
        knightMovements.add(new JumpMove(1, -2));
        knightMovements.add(new JumpMove(-1, 2));
        knightMovements.add(new JumpMove(-1, -2));

        whitePieces.add(new PieceCoord(new Coordinate(2, 1), pieceFactory.createPiece("knight", knightMovements, false, SideColor.White)));
        whitePieces.add(new PieceCoord(new Coordinate(9, 1), pieceFactory.clonePiece("knight", SideColor.White)));
    }

    private void createWhiteBishops(List<PieceCoord> whitePieces, PieceFactory pieceFactory) {
        List<Move> bishopMovements = new ArrayList<>();
        bishopMovements.add(new DiagonalMove());

        whitePieces.add(new PieceCoord(new Coordinate(4, 1), pieceFactory.createPiece("bishop", bishopMovements, false, SideColor.White)));
        whitePieces.add(new PieceCoord(new Coordinate(7, 1), pieceFactory.clonePiece("bishop", SideColor.White)));
    }

    private void createWhiteRooks(List<PieceCoord> whitePieces, PieceFactory pieceFactory) {
        List<Move> rookMovements = new ArrayList<>();
        rookMovements.add(new VerticalMove(true));
        rookMovements.add(new HorizontalMove());

        whitePieces.add(new PieceCoord(new Coordinate(1, 1), pieceFactory.createPiece("rook", rookMovements, false, SideColor.White)));
        whitePieces.add(new PieceCoord(new Coordinate(10, 1), pieceFactory.clonePiece("rook", SideColor.White)));
    }

    private void createWhitePawns(List<PieceCoord> whitePieces, PieceFactory pieceFactory, Integer quantity) {
        List<Move> pawnMovements = new ArrayList<>();
        pawnMovements.add(new VerticalMove(1, false));
        pawnMovements.add(new InitialVerticalMove(2, false));
        List<Move> pawnEatMoves = new ArrayList<>();
        pawnEatMoves.add(new DiagonalMove(1, -1,false));
        pawnEatMoves.add(new DiagonalMove(1, 1,false));

        for (int i = 1; i <= quantity; i++) {
            whitePieces.add(new PieceCoord(new Coordinate(i, 2), pieceFactory.createPiece("pawn",pawnMovements,pawnEatMoves,false, SideColor.White)));
        }
    }
}
