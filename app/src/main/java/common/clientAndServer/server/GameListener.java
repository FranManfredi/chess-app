package common.clientAndServer.server;

import edu.austral.dissis.chess.gui.GameEventListener;
import edu.austral.dissis.chess.gui.Move;
import org.jetbrains.annotations.NotNull;

public class GameListener implements GameEventListener {

    private final GameServer gameServer;

    public GameListener(GameServer gameServer) {
        this.gameServer = gameServer;
    }

    @Override
    public void handleMove(@NotNull Move move) {
        try {
            if (gameServer != null) {
                gameServer.handleMove(move);
            } else {
                // Handle the case where gameServer is unexpectedly null.
                System.err.println("GameServer is null in GameListener.handleMove");
            }
        } catch (Exception e) {
            // Handle the exception appropriately (log it, show an error message, etc.).
            e.printStackTrace();
        }
    }
}

