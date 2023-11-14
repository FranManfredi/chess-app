package chess.game.classic;

import common.models.Coordinate;
import common.models.SideColor;
import common.models.Game;
import common.models.Player;
import common.models.Piece;
import common.moves.DiagonalMove;

import chess.game.classic.condition.ClassicWinCondition;
import chess.game.classic.builder.PieceBuilder;

import chess.moves.HorizontalMove;
import chess.moves.JumpMove;
import chess.moves.VerticalMove;

import java.util.ArrayList;
import java.util.List;

public class ClassicGame {

    public static Game createGame() {
        Player player1 = new Player();
        Player player2 = new Player();
        List<Piece> blackPieces = new ArrayList<>();
        List<Piece> whitePieces = new ArrayList<>();
        PieceBuilder pieceBuilder = new PieceBuilder();

        // Define movements for the pieces
        List<Move> pawnMovements = new ArrayList<>();
        pawnMovements.add(new VerticalMove(1, false));
        pawnMovements.add(new VerticalMove(2, false));

        List<Move> pawnEatMoves = new ArrayList<>();
        pawnEatMoves.add(new DiagonalMove(1, -1));
        pawnEatMoves.add(new DiagonalMove(1, 1));

        List<Move> rookMovements = new ArrayList<>();
        rookMovements.add(new VerticalMove(true));
        rookMovements.add(new HorizontalMove(false));

        List<Move> bishopMovements = new ArrayList<>();
        bishopMovements.add(new DiagonalMove());

        List<Move> queenMovements = new ArrayList<>();
        queenMovements.add(new VerticalMove(true));
        queenMovements.add(new HorizontalMove(false));
        queenMovements.add(new DiagonalMove());

        List<Move> knightMovements = new ArrayList<>();
        knightMovements.add(new JumpMove(2, 1));
        knightMovements.add(new JumpMove(2, -1));
        knightMovements.add(new JumpMove(-2, 1));
        knightMovements.add(new JumpMove(-2, -1));
        knightMovements.add(new JumpMove(1, 2));
        knightMovements.add(new JumpMove(1, -2));
        knightMovements.add(new JumpMove(-1, 2));
        knightMovements.add(new JumpMove(-1, -2));

        List<Move> kingMovements = new ArrayList<>();
        kingMovements.add(new VerticalMove(1, true));
        kingMovements.add(new HorizontalMove(1, false));
        kingMovements.add(new DiagonalMove(1, 1));

        // Create white pieces
        whitePieces.add(pieceBuilder.createPiece("pawn", new Coordinate(1, 2), pawnMovements, pawnEatMoves, false, SideColor.White));
        // ... (create other white pieces)

        // Create black pieces
        blackPieces.add(pieceBuilder.clonePiece("pawn", new Coordinate(1, 7), SideColor.Black));
        // ... (create other black pieces)

        // Create the board
        ChessBoard board = new ChessBoard(8, 8, blackPieces, whitePieces);

        // Create the game with players, board, and win condition
        return new Game(player1, player2, board, SideColor.White, new ClassicWinCondition());
    }
}