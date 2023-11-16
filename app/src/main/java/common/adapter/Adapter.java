package common.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import common.models.Board;
import common.models.BoardPosition;
import common.models.Coordinate;
import common.models.PieceName;
import common.models.SideColor;
import edu.austral.dissis.chess.gui.*;

public class Adapter {
    public static List<ChessPiece> getCurrentPieces(Board board) {
        List<ChessPiece> pieces = new ArrayList<>();
        for (BoardPosition square : board.getSquares()) {
            if (!Objects.equals(square.getPiece().getName(), PieceName.NULL))
                pieces.add(new ChessPiece(String.valueOf(square.getPiece().getId()),convertPlayerColor(square.getPiece().getColor()), convertCoordinateToPosition(square.getCoordinate()), square.getPiece().getName().toString().toLowerCase()));
            }
        return pieces;
    }

    public static Position convertCoordinateToPosition(Coordinate initialSquare) {
        return new Position(initialSquare.row(), initialSquare.column());
    }

    public static Coordinate convertPositionToCoordinate(Position position) {
        return new Coordinate(position.component2(), position.component1());
    }

    public static PlayerColor convertPlayerColor(SideColor color) {
        if (color == SideColor.Black) {
            return PlayerColor.BLACK;
        } else {
            return PlayerColor.WHITE;
        }
    }

    public static BoardSize getBoardSize(Board board) {
        return new BoardSize(board.getColumns(), board.getRows());
    }


}