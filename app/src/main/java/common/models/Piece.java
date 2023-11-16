package common.models;

import common.logic.CheckLegalMove;
import common.logic.WinCondition;
import common.moves.Move;
import common.results.MoveResults;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import chess.logic.CommonMoveValidator;

public class Piece {
    private final PieceName pieceName;
    private final Coordinate initialSquare;
    private final List<Move> movements;
    private final List<Move> eatMovements;
    private final SideColor color;
    private final boolean isImportant;
    private final int id;

    public Piece(PieceName string, Coordinate initialSquare, List<Move> movements, List<Move> eatMovements, SideColor color, boolean isImportant, int id) {
        this.pieceName = string;
        this.initialSquare = initialSquare;
        this.color = color;
        this.movements = movements;
        this.eatMovements = new ArrayList<>(eatMovements); // Crear una copia para evitar referencias compartidas
        this.isImportant = isImportant;
        this.id = id;
    }

    public Piece(PieceName pieceName, Coordinate initialSquare, List<Move> movements, SideColor color, boolean isImportant, int id) {
        this(pieceName, initialSquare, movements, movements, color, isImportant, id);
    }

    public MoveResults<Board, Boolean> movePiece(Coordinate initial, Coordinate toSquare, Board board, WinCondition winCondition, CheckLegalMove checkLegalMove) {
        if (!CommonMoveValidator.isValidMove(board, this, toSquare)) {
            return new MoveResults<>(board, true, "Common Rule unfollowed");
        }

        List<Move> validMovements = Objects.equals(board.getSquare(toSquare).getPiece().getName(), PieceName.NULL) ? movements : eatMovements;
        return checkLegalMove.checkIfMoveIsLegal(winCondition, this, initial, board, toSquare, validMovements);
    }

    public PieceName getName() {
        return pieceName;
    }

    public Coordinate getInitialSquare() {
        return initialSquare;
    }

    public boolean isImportant() {
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