package common.adapter;
import common.models.*;
import common.results.MoveResult;
import edu.austral.dissis.chess.gui.*;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class DefaultGameEngine implements GameEngine {
    private Game game;

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

        MoveResult<Game, Boolean, SideColor> moveResult = game.movePiece(initialCoordinate, finalCoordinate);

        if (moveResult.errorResult()) {
            if (moveResult.message().equals("CheckMate")) {
                return new GameOver(Adapter.convertPlayerColor(game.getTurnHandler().getTurn()));
            } else {
                return new InvalidMove(moveResult.message());
            }
        } else {
            Game newGame = moveResult.successfulResult();
            Board board = newGame.getBoard();
            TurnHandler turnHandler = newGame.getTurnHandler();
            List<ChessPiece> pieces = Adapter.getCurrentPieces(board);
            PlayerColor playerColor = Adapter.convertPlayerColor(newGame.getTurnHandler().getTurn());

            // Create a new Game instance with the updated board
            this.game = new Game(board, turnHandler.getTurn(), newGame.getWinCondition(), newGame.getLegalMove(), newGame.getSpecialConditions());

            return new NewGameState(pieces, playerColor);
        }
    }



    @NotNull
    @Override
    public InitialState init() {
        return new InitialState(
                Adapter.getBoardSize(game.getBoard()),
                Adapter.getCurrentPieces(game.getBoard()),
                Adapter.convertPlayerColor(game.getTurnHandler().getTurn())
        );
    }
}

