package common.models;

import common.moves.Move;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PieceFactory {
    private int id = 1;
    private List<Move> movements;
    private List<Move> eatMovements;
    private boolean isImportant;
    private final List<Piece> pieces = new ArrayList<>();

    public Piece createPiece(String pieceName, List<Move> movements, List<Move> eatMovements, boolean isImportant, SideColor color) {
        this.movements = movements;
        this.eatMovements = eatMovements;
        this.isImportant = isImportant;
        Piece newPiece = new Piece(pieceName, movements, eatMovements, color, isImportant,this.id);
        pieces.add(newPiece);
        this.id ++;
        return newPiece;
    }

    public Piece createPiece(String pieceName, List<Move> movements, boolean isImportant, SideColor color) {
        this.movements = movements;
        this.eatMovements = movements;
        this.isImportant = isImportant;
        this.id ++;
        Piece newPiece = new Piece(pieceName, movements, color, isImportant, this.id);
        pieces.add(newPiece);
        return newPiece;
    }
     public Piece clonePiece(String pieceName,SideColor color){
        for (Piece piece : pieces) {
            if (Objects.equals(piece.getName(), pieceName)){
                this.movements = piece.getMovements();
                this.eatMovements = piece.getEatMovements();
                this.isImportant = piece.isImportant();
                this.id++;
                return new Piece(pieceName, movements, eatMovements, color, isImportant, id);
            }
        }
         return createNullPiece();
     }

     public Piece createNullPiece(){
        return new Piece("null", new ArrayList<>(), new ArrayList<>(), SideColor.NULL, false, 0);
     }

    public Piece promotePawn(SideColor color, int id) {
        for (Piece piece : pieces) {
            if (piece.getName().equals("queen")){
                this.movements = piece.getMovements();
                this.eatMovements = piece.getEatMovements();
                this.isImportant = piece.isImportant();
                return new Piece("queen", movements, eatMovements, color, isImportant, id);
            }
        }
        return createNullPiece();
    }
}
