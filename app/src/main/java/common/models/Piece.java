package common.models;

import chess.logic.*;
import common.logic.CheckLegalMove;
import common.logic.WinCondition;
import common.moves.Move;
import common.results.MoveResults;

import java.util.List;
import java.util.Objects;

public class Piece{
    private final PieceName pieceName;
    private final Coordinate initialSquare;
    private final List<Move> movements;
    private final List<Move> eatMovements;
    private final SideColor color;
    private final Boolean isImportant;
    private final int id;

    public Piece(PieceName pieceName, Coordinate initialSquare, List<Move> movements, List<Move> eatMovements, SideColor color, boolean isImportant, int id) {
        this.pieceName = pieceName;
        this.initialSquare = initialSquare;
        this.color = color;
        this.movements = movements;
        this.eatMovements = eatMovements;
        this.isImportant = isImportant;
        this.id = id;
    }

    public Piece(PieceName pieceName, Coordinate initialSquare, List<Move> movements, SideColor color, boolean isImportant, int id) {
        this.pieceName = pieceName;
        this.initialSquare = initialSquare;
        this.color = color;
        this.movements = movements;
        this.eatMovements = movements;
        this.isImportant = isImportant;
        this.id = id;
    }

    public MoveResults<Board, Boolean> movePiece(Coordinate initial, Coordinate toSquare, Board board, WinCondition winCondition, CheckLegalMove checkLegalMove) {
        if (!CommonMoveValidator.isValidMove(board, this, toSquare)) {
            return new MoveResults<>(board, true, "Common Rule unfollowed");
        }
        if (!Objects.equals(board.getSquare(toSquare).getPiece().getName(), PieceName.NULL)) {
            return checkLegalMove.check(this,toSquare, board, initial, eatMovements,winCondition);
        } else {
            return checkLegalMove.check(this,toSquare, board, initial, movements,winCondition);
        }
    }

    public PieceName getName() {
        return pieceName;
    }

    public Coordinate getInitialSquare() {
        return initialSquare;
    }

    public Boolean isImportant() {
        return isImportant;
    }

    public SideColor getColor() {
        return color;
    }

    public List<Move> getEatMovements() {
        return eatMovements;
    }

    public List<Move> getMovements() {
        return movements;
    }

    public int getId() {
        return id;
    }

}