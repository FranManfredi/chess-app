package common.clientAndServer.server;

import edu.austral.ingsis.clientserver.ServerConnectionListener;
import org.jetbrains.annotations.NotNull;

public class GameServerListener implements ServerConnectionListener {

    private final GameServer gameServer;

    public GameServerListener(GameServer gameServer) {
        this.gameServer = gameServer;
    }

    @Override
    public void handleClientConnection(@NotNull String s) {
        try {
            if (gameServer != null) {
                gameServer.handleNewGame();
            } else {
              
                System.err.println("GameServer is null in GameServerListener.handleClientConnection");
            }
        } catch (Exception e) {
           
            e.printStackTrace();
        }
    }

    @Override
    @SuppressWarnings("unused")
    public void handleClientConnectionClosed(@NotNull String s) {
        
    }
}
