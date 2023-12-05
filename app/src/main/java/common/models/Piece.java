package common.models;

import common.moves.Move;

import java.util.List;

public class Piece{
    private final String pieceName;
    private final List<Move> movements;
    private final List<Move> eatMovements;
    private final SideColor color;
    private final Boolean isImportant;
    private final int id;

    public Piece(String pieceName, List<Move> movements, List<Move> eatMovements, SideColor color, boolean isImportant, int id) {
        this.pieceName = pieceName;
        this.color = color;
        this.movements = movements;
        this.eatMovements = eatMovements;
        this.isImportant = isImportant;
        this.id = id;
    }

    public Piece(String pieceName, List<Move> movements, SideColor color, boolean isImportant, int id) {
        this.pieceName = pieceName;
        this.color = color;
        this.movements = movements;
        this.eatMovements = movements;
        this.isImportant = isImportant;
        this.id = id;
    }

    public String getName() {
        return pieceName;
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
