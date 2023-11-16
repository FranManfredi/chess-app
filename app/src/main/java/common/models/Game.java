package common.models;

import common.logic.CheckLegalMove;
import common.logic.WinCondition;
import common.results.MoveResults;

import java.util.Objects;
import java.util.Random;
import java.util.Stack;

public class Game {
    private final Player player1;
    private final Player player2;
    private final Stack<Board> boardStack = new Stack<>();
    private TurnHandler turnHandler;
    private final WinCondition winCondition;
    private final CheckLegalMove checkLegalMove;

    public Game(Player player1, Player player2, Board initialBoard, SideColor startingPlayer, WinCondition winCondition) {
        this.player1 = player1;
        this.player2 = player2;
        this.boardStack.push(initialBoard);
        this.turnHandler = new TurnHandler(startingPlayer);
        this.winCondition = winCondition;
        this.checkLegalMove = new CheckLegalMove();
        initializeGame();
    }

    private void initializeGame() {
        System.out.println("Game Started");
        Random rand = new Random();
        int randomNum = rand.nextInt(2);
        if (randomNum == 0) {
            player1.setColor(SideColor.White);
            player2.setColor(SideColor.Black);
        } else {
            player1.setColor(SideColor.Black);
            player2.setColor(SideColor.White);
        }
    }

    public MoveResults<Board, Boolean> movePiece(Coordinate initial, Coordinate toSquare, Player currentPlayer) {
        Piece piece = boardStack.peek().getSquare(initial).getPiece();
        if (Objects.isNull(piece)) {
            return new MoveResults<>(boardStack.peek(), true, "Piece not found");
        }
        if (piece.getColor().equals(currentPlayer.getColor())) {
            MoveResults<Board, Boolean> result = piece.movePiece(initial, toSquare, boardStack.peek(), winCondition, checkLegalMove);
            if (result.errorResult()) {
                return new MoveResults<>(boardStack.peek(), true, result.message());
            }
            turnHandler = turnHandler.nextTurn();
            boardStack.push(result.successfulResult());
            return new MoveResults<>(boardStack.peek(), false, result.message());
        } else {
            return new MoveResults<>(boardStack.peek(), true, "Piece not the same color as the player");
        }
    }

    public Board getBoard() {
        return boardStack.peek();
    }

    public TurnHandler getTurnHandler() {
        return turnHandler;
    }

    public Player getCurrentPlayer() {
        return (turnHandler.turn() == player1.getColor()) ? player1 : player2;
    }
}
