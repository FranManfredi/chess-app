package common.models;

import common.logic.CommonRule;
import common.logic.LegalMove;
import common.logic.WinCondition;
import common.results.MoveResult;


import java.util.Objects;
import java.util.Random;
import java.util.Stack;

public class Game {
    private final Player player1;
    private final Player player2;
    private final Stack<Board> boardStack = new Stack<>();
    private TurnHandler turnHandler;
    private final WinCondition winCondition;
    private final LegalMove legalMove;


    public Game(Board board, SideColor startingPlayer, WinCondition winCondition, LegalMove legalMove) {
        this.player1 = new Player();
        this.player2 = new Player();
        this.boardStack.push(board);
        this.turnHandler = new TurnHandler(startingPlayer);
        this.winCondition = winCondition;
        this.legalMove = legalMove;
        setGame();
    }

    public void setGame() {
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

    public MoveResult<Board,Boolean,SideColor> movePiece(Coordinate initial, Coordinate toSquare) {
        Piece piece = boardStack.peek().getPiece(initial).successfulResult().get();
        if (isNotNull(piece)) {
            return new MoveResult<>(boardStack.peek(), true,turnHandler.getTurn(), "Piece not found");
        }
        if(piece.getColor().equals(turnHandler.getTurn())) {
            MoveResult<Board,Boolean,SideColor> res = movePiece(initial,toSquare, boardStack.peek(),winCondition, legalMove);
            if (res.errorResult()) {
                return new MoveResult<>(boardStack.peek(), true, turnHandler.getTurn(), res.message());
            }
            turnHandler = turnHandler.setTurn(res.nextTurn());
            boardStack.push(res.successfulResult());
            return new MoveResult<>(boardStack.peek(), false,res.nextTurn(), res.message());
        }
        else{
            return new MoveResult<>(boardStack.peek(), true, turnHandler.getTurn(),"Piece not same color as player");
        }
    }

    private boolean isNotNull(Piece piece) {
        return Objects.equals(piece.getName(), "null");
    }


    public Board getBoard() {
        return boardStack.peek();
    }

    public TurnHandler getTurnHandler() {
        return turnHandler;
    }

    public MoveResult<Board, Boolean, SideColor> movePiece(Coordinate initial, Coordinate toSquare, Board board, WinCondition winCondition, LegalMove LegalMove) {
        Piece piece = board.getPiece(initial).successfulResult().get();
        if (notFollowsCommonRule(piece,toSquare, board)) {
            return new MoveResult<>(board, true,piece.getColor(), "Common Rule unfollowed");
        }
        if (isNotNull(toSquare, board)) {
            return LegalMove.movePiece(piece,toSquare, board, initial, piece.getEatMovements(),winCondition);
        } else {
            return LegalMove.movePiece(piece,toSquare, board, initial, piece.getMovements(),winCondition);
        }
    }

    private boolean isNotNull(Coordinate toSquare, Board board) {
        return !Objects.equals(board.getPiece(toSquare).successfulResult().get().getName(), "null");
    }

    private boolean notFollowsCommonRule(Piece piece,Coordinate toSquare, Board board) {
        CommonRule commonRule = new CommonRule();
        return !commonRule.checkRule(board, piece, toSquare);
    }
}
