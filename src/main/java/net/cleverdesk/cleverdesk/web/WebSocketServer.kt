package net.cleverdesk.cleverdesk.web

import com.google.gson.Gson
import net.cleverdesk.cleverdesk.User
import org.eclipse.jetty.websocket.api.Session
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage
import org.eclipse.jetty.websocket.api.annotations.WebSocket
import java.util.*
import java.util.concurrent.ConcurrentLinkedQueue

/**
 * provides an api interface to access all function in realtime.
 */
@WebSocket
public class WebSocketServer() {



    //Stores and manages session
    private var sessions: Queue<Session> = ConcurrentLinkedQueue<Session>();

    @OnWebSocketConnect
    open fun onConnect(session: Session) {
        sessions.add(session)

    }

    @OnWebSocketClose
    public open fun onClose(session: Session, statusCode: Int, reason: String) {
        sessions.remove(session)
    }

    @OnWebSocketMessage
    public open fun onMessage(session: Session, message: String) {
        val received_message = Gson().fromJson(message, WebMessage::class.java)
        net.cleverdesk.cleverdesk.web.WebSocket.handlerManager.handleMessage(object : WebResponseProvider {
            override var user: User? = null

            override fun saveInSession(key: String, value: Any) {
                throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun sendMessage(channel: String, message: Any) {
                val msg = WebMessage(message, channel, received_message!!.request_id)
                session.remote.sendString(Gson().toJson(msg))
            }

            override fun sendMessage(channel: String, message: String) {
                val msg = WebMessage(message, channel, received_message!!.request_id)
                session.remote.sendString(Gson().toJson(msg))
            }

        }, received_message)
    }
}