package common.adapter;
import common.models.Board;
import common.models.Coordinate;
import common.models.Game;
import common.models.SideColor;
import common.results.MoveResult;
import edu.austral.dissis.chess.gui.*;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class DefaultGameEngine implements GameEngine {
    private final Game game;
    public DefaultGameEngine(Game game) {
        this.game = game;
    }
    @NotNull
    @Override
    public edu.austral.dissis.chess.gui.MoveResult applyMove(@NotNull Move move) {
        Position initialPosition = move.component1();
        Position finalPosition = move.component2();
        Coordinate initialCoordinate = Adapter.convertPositionToCoordinate(initialPosition);
        Coordinate finalCoordinate = Adapter.convertPositionToCoordinate(finalPosition);

        MoveResult<Board,Boolean, SideColor> moveResult = game.movePiece(initialCoordinate,finalCoordinate);
        if (moveResult.errorResult()) {
            if (moveResult.message().equals("CheckMate")) {
                return new GameOver(Adapter.convertPlayerColor(game.getTurnHandler().turn()));
            } else {
                return new InvalidMove(moveResult.message());
            }
        }else {
            Board board = moveResult.successfulResult();
            List<ChessPiece> pieces = Adapter.getCurrentPieces(board);
            PlayerColor playerColor = Adapter.convertPlayerColor(game.getTurnHandler().turn());
            return new NewGameState(pieces,playerColor);
        }
    }

    @NotNull
    @Override
    public InitialState init() {
        return new InitialState(Adapter.getBoardSize(game.getBoard()), Adapter.getCurrentPieces(game.getBoard()),Adapter.convertPlayerColor(game.getTurnHandler().turn()));
    }

}
