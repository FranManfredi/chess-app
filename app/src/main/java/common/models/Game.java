package common.models;

import common.logic.CommonRule;
import common.logic.LegalMove;
import common.logic.SpecialCondition;
import common.logic.WinCondition;
import common.results.MoveResult;


import java.util.Objects;
import java.util.Random;

public class Game {
    private final Player player1;
    private final Player player2;
    private final BoardStates boardStack;
    private final TurnHandler turnHandler;
    private final WinCondition winCondition;
    private final LegalMove legalMove;
    private final SpecialCondition specialCondition;

    public Game(Board board, SideColor startingPlayer, WinCondition winCondition, LegalMove legalMove, SpecialCondition specialCondition) {
        this.player1 = new Player(SideColor.White);
        this.player2 = new Player(SideColor.Black);
        this.boardStack = new BoardStates().push(board);
        this.turnHandler = new TurnHandler(startingPlayer);
        this.winCondition = winCondition;
        this.specialCondition = specialCondition;
        this.legalMove = legalMove;
        setGame();
    }

    private Game(Player player1, Player player2, BoardStates boardStack, TurnHandler turnHandler,
                 WinCondition winCondition, LegalMove legalMove, SpecialCondition specialCondition) {
        this.player1 = player1;
        this.player2 = player2;
        this.boardStack = boardStack;
        this.turnHandler = turnHandler;
        this.winCondition = winCondition;
        this.specialCondition = specialCondition;
        this.legalMove = legalMove;
    }

    public Game withBoardAndTurnHandler(Board newBoard, TurnHandler newTurnHandler) {
        return new Game(player1, player2, boardStack.push(newBoard), newTurnHandler, winCondition, legalMove, specialCondition);
    }

    public Game withBoard(Board newBoard) {
        return new Game(player1, player2, boardStack.push(newBoard), turnHandler, winCondition, legalMove, specialCondition);
    }

    private void setGame() {
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

    public MoveResult<Game, Boolean, SideColor> movePiece(Coordinate initial, Coordinate toSquare) {
        // Retrieve the current board from the stack
        Board currentBoard = boardStack.peek();

        // Find the piece at the initial coordinate
        Piece piece = currentBoard.getPiece(initial).successfulResult().orElse(null);

        // Check if the piece is found
        if (piece == null || !piece.getColor().equals(turnHandler.getTurn())) {
            // Piece not found or not the correct color
            return new MoveResult<>(withBoard(currentBoard), true, turnHandler.getTurn(), "Piece not found or not the correct color");
        }

        // Attempt to make the move
        MoveResult<Board, Boolean, SideColor> res = movePieceRecursive(initial, toSquare, currentBoard, winCondition, legalMove, specialCondition);

        // Check the result of the move
        if (res.errorResult()) {
            // Move was unsuccessful
            return new MoveResult<>(withBoard(currentBoard), true, turnHandler.getTurn(), res.message());
        } else {
            // Move was successful
            boardStack.push(res.successfulResult());
            return new MoveResult<>(withBoardAndTurnHandler(res.successfulResult(), new TurnHandler(res.nextTurn())),
                    false, res.nextTurn(), res.message());
        }
    }

    private MoveResult<Board, Boolean, SideColor> movePieceRecursive(Coordinate initial, Coordinate toSquare, Board board, WinCondition winCondition, LegalMove legalMove, SpecialCondition specialCondition) {
        // Find the piece at the initial coordinate
        Piece piece = board.getPiece(initial).successfulResult().orElse(null);

        // Check if the piece is found
        if (piece == null) {
            return new MoveResult<>(board, true, turnHandler.getTurn(), "Piece not found");
        }

        // Check if the piece is the correct color
        if (!piece.getColor().equals(turnHandler.getTurn())) {
            // Piece not the correct color
            return new MoveResult<>(board, true, turnHandler.getTurn(), "Piece not the correct color");
        }

        if (checkForSpecialConditions(specialCondition)) {

        }
        else if (notFollowsCommonRule(piece, toSquare, board)) { // Check if the piece can move to the toSquare
            // Piece cannot move to the toSquare
            return new MoveResult<>(board, true, turnHandler.getTurn(), "Piece cannot move to the Square");
        }

        return legalMove.movePiece(piece, toSquare, board, initial, winCondition, specialCondition);
    }

    private boolean checkForSpecialConditions(SpecialCondition specialCondition) {
        return true;
    }

    public Board getBoard() {
        return boardStack.peek();
    }

    private boolean notFollowsCommonRule(Piece piece, Coordinate toSquare, Board board) {
        CommonRule commonRule = new CommonRule();
        return !commonRule.checkRule(board, piece, toSquare);
    }

    public TurnHandler getTurnHandler() {
        return turnHandler;
    }

    public WinCondition getWinCondition() {
        return this.winCondition;
    }

    public LegalMove getLegalMove() {
        return this.legalMove;
    }

    public SpecialCondition getSpecialConditions() {
        return this.specialCondition;
    }
}
