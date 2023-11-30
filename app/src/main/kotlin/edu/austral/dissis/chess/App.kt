package edu.austral.dissis.chess


import chess.logic.classic.ClassicGame
import common.adapter.DefaultGameEngine
import common.client_server.server.GameServer
import edu.austral.dissis.chess.gui.CachedImageResolver
import edu.austral.dissis.chess.gui.DefaultImageResolver
import edu.austral.dissis.chess.gui.GameView
import edu.austral.ingsis.clientserver.netty.server.NettyServerBuilder
import javafx.application.Application
import javafx.application.Application.launch
import javafx.scene.Scene
import javafx.stage.Stage


fun main() {
    launch(ChessGameApplication::class.java)
}

class ChessGameApplication : Application() {
    private val gameEngine = DefaultGameEngine(ClassicGame.CreateGame())
    private val imageResolver = CachedImageResolver(DefaultImageResolver())
    private val root = GameView(imageResolver)
    private val server = NettyServerBuilder.createDefault()
    private val gameServer : GameServer = GameServer(gameEngine, root, server)

    companion object {
        const val GameTitle = "Chess"
    }

    override fun start(primaryStage: Stage) {
        primaryStage.title = GameTitle

        primaryStage.scene = Scene(root)

        primaryStage.show()
    }
}