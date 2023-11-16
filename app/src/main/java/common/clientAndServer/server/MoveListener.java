package common.clientAndServer.server;

import edu.austral.dissis.chess.gui.GameEventListener;
import edu.austral.dissis.chess.gui.Move;
import edu.austral.ingsis.clientserver.Message;
import edu.austral.ingsis.clientserver.MessageListener;
import org.jetbrains.annotations.NotNull;

public class MoveListener implements MessageListener<Move> {
    private final GameEventListener gameListener;

    public MoveListener(GameEventListener gameListener) {
        this.gameListener = gameListener;
    }

    @Override
    public void handleMessage(@NotNull Message<Move> message) {
        try {
            if (gameListener != null) {
                gameListener.handleMove(message.getPayload());
            } else {
                System.err.println("GameEventListener is null in MoveListener.handleMessage");
            }
        } catch (Exception e) {
                        e.printStackTrace();
        }
    }
}
