package checkers.logic;

import common.logic.LegalMove;
import common.logic.PieceMover;
import common.logic.PossibleMovements;
import common.logic.WinCondition;
import common.models.*;
import common.moves.Move;
import common.results.MoveResult;

import java.util.ArrayList;
import java.util.List;

public class CheckersLegalMove implements LegalMove {
    private final PieceMover pieceMover = new PieceMover();
    private final PossibleMovements possibleMovements = new PossibleMovements();

    @Override
    public MoveResult<Board, Boolean, SideColor> movePiece(Piece piece, Coordinate toSquare, Board board, Coordinate initial, List<Move> moves, WinCondition winCondition) {
        return loopUntilCantEat(piece, toSquare, board, initial, moves, winCondition);
    }

    private MoveResult<Board, Boolean, SideColor> loopUntilCantEat(Piece piece, Coordinate toSquare, Board board, Coordinate initial, List<Move> moves, WinCondition winCondition) {
        if (canEatRule(piece, board, toSquare)) {
            return new MoveResult<>(board, true,piece.getColor(), "always eat Rule unfollowed");
        }
        MoveResult<Board, Boolean, SideColor> move = pieceMover.check(board, initial, toSquare, moves, piece, board.getPiece(toSquare).successfulResult().get());
        if (move.errorResult())
            return move;
        else {
            if (canContinueToEat(piece,move.successfulResult(),toSquare) != toSquare && hasEaten(piece,toSquare,board))
                return winCondition.checkWin(board, piece,  new MoveResult<>(move.successfulResult(), false, piece.getColor(), "Piece Moved"), toSquare);
            else
                return winCondition.checkWin(board, piece, move, toSquare);
        }
    }

    private boolean hasEaten(Piece piece,Coordinate toSquare, Board board) {
        SideColor color = board.getPiece(toSquare).successfulResult().get().getColor();
        if (color == SideColor.Black)
            return piece.getColor() == SideColor.White;
        else if (color == SideColor.White)
            return piece.getColor() == SideColor.Black;
        else
            return false;
    }

    private Coordinate canContinueToEat(Piece piece, Board board, Coordinate toSquare) {
        List<Coordinate> possibleMoves = new ArrayList<>(possibleMovements.getPossibleMovements(board, piece, board.getCoordOfPiece(piece).successfulResult().get()));
        for (Coordinate possibleMove : possibleMoves) {
            if (checkIfPossible(piece, board, possibleMove)) {
                return possibleMove;
            }
        }
        return toSquare;
    }

    private boolean canEatRule(Piece piece, Board board, Coordinate toSquare) {
        List<PieceCoord> possibleMoves = new ArrayList<>();
        for (Piece piece1 : board.getCurrentPieces()) {
            if (piece1.getColor() == piece.getColor())
                for (Coordinate possibleMove : possibleMovements.getPossibleMovements(board, piece1, board.getCoordOfPiece(piece1).successfulResult().get())) {
                    possibleMoves.add(new PieceCoord(possibleMove, piece1));
                }
        }
        for (PieceCoord possibleMove : possibleMoves) {
            if (isNotEatingWhenPossible(possibleMove.piece(), board, toSquare,possibleMove.coord())) {
                return true;
            }
        }
        return false;
    }
    private boolean isNotEatingWhenPossible(Piece piece, Board board, Coordinate toSquare, Coordinate possibleMove) {
        boolean canEat = checkIfPossible(piece, board, possibleMove);
        boolean position = possibleMove.column() == toSquare.column() && possibleMove.row() == toSquare.row();
        return canEat && !position;
    }

    private boolean checkIfPossible(Piece piece, Board board, Coordinate possibleMove) {
        for (Move move : piece.getEatMovements()) {
            if (move.checkMove(board.getCoordOfPiece(piece).successfulResult().get(), possibleMove, board, piece.getColor()).outputResult() && board.checkForPieceInSquare(possibleMove)) {
                return true;
            }
        }
        return false;
    }
}
