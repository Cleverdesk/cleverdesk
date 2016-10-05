package net.cleverdesk.cleverdesk.web

import com.google.gson.Gson
import net.cleverdesk.cleverdesk.launcher.Launcher
import org.eclipse.jetty.websocket.api.Session
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage
import java.util.*
import java.util.concurrent.ConcurrentLinkedQueue

/**
 * provides an api interface to access all function in realtime.
 */
class WebSocket(handlerManager: WebHandlerManager, launcher: Launcher) {

    private val handlerManager = handlerManager
    private val launcher = launcher


    //Stores and manages session
    private var sessions: Queue<Session> = ConcurrentLinkedQueue<Session>();

    @OnWebSocketConnect
    open fun connected(session: Session) {
        sessions.add(session)

    }

    @OnWebSocketClose
    open fun closed(session: Session, statusCode: Int, reason: String) {
        sessions.remove(session)
    }

    @OnWebSocketMessage
    open fun message(session: Session, message: String) {
        val received_message = Gson().fromJson(message, WebMessage::class.java)
        handlerManager.handleMessage(object : WebResponseProvider {
            override fun sendMessage(channel: String, message: Any) {
                val msg = WebMessage()
                msg.channel = channel
                msg.message = message
                msg.user = received_message!!.user
                session.remote.sendString(Gson().toJson(msg))
            }

            override fun sendMessage(channel: String, message: String) {
                val msg = WebMessage()
                msg.channel = channel
                msg.message = message
                msg.user = received_message!!.user
                session.remote.sendString(Gson().toJson(msg))

            }

        }, received_message)
    }
}