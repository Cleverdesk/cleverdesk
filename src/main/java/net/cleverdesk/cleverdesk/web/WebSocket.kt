package net.cleverdesk.cleverdesk.web

import net.cleverdesk.cleverdesk.launcher.Launcher
import spark.Spark.*
/**
 * Created by SV on 11.10.2016.
 */

object WebSocket {
    public open val handlerManager = WebHandlerManager()
    open fun start(launcher: Launcher, port: Int) {
        webSocket("/ws", WebSocketServer::class.java)
        staticFileLocation("/static")
        init()
    }
}
