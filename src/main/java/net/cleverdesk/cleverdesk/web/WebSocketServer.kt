/**
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package net.cleverdesk.cleverdesk.web

import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import org.eclipse.jetty.websocket.api.Session
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage
import org.eclipse.jetty.websocket.api.annotations.WebSocket

/**
 * provides an api interface to access all functions in time.
 */
@WebSocket
public class WebSocketServer() {



    //Stores and manages session
    private var sessions: MutableMap<Session, WebSession<Session>> = mutableMapOf()


    /**
     * Will be executed if a new session starts or connects.
     */
    @OnWebSocketConnect
    open fun onConnect(session: Session) {
        sessions.put(session, WebSession(session))


    }

    /**
     * Will be executed if a socket closes his session.
     */
    @OnWebSocketClose
    public open fun onClose(session: Session, statusCode: Int, reason: String) {
        sessions.remove(session)
    }

    /**
     * Handles a WebSocket-message.
     * 1. Check if session is registered
     * 1b. If not register the session.
     * 2. Parse message into a [WebMessage] or send an error-report.
     * 3. redirect the message to the channel-based [WebHandler]
     */
    @OnWebSocketMessage
    public open fun onMessage(session: Session, message: String) {
        try {
            if (!sessions.containsKey(session)) {
                sessions.put(session, WebSession(session))
            }

            //Convert JSON-message to a WebMessage-object.
            val received_message = Gson().fromJson(message, WebMessage::class.java)
            //Search a handler for [received_message]
            net.cleverdesk.cleverdesk.web.WebSocket.handlerManager.handleMessage(object : WebResponseProvider {

                /**
                 * Redirect [message] as JSON to the sender's [channel].
                 */
                override fun sendMessage(channel: String, message: Any) {
                    val msg = WebMessage(message, channel, received_message!!.request_id)

                    session.remote.sendString(Gson().toJson(msg))
                }

                /**
                 * Redirect [message] to the sender's [channel].
                 */
                override fun sendMessage(channel: String, message: String) {
                    val msg = WebMessage(message = message, channel = channel, request_id = received_message!!.request_id)
                    session.remote.sendString(Gson().toJson(msg))
                }

            }, sessions.get(session)!!, received_message)
        } catch (ex: JsonSyntaxException) {
            //Message is not JSON-conform.
            val error_message = WebMessage("Please use the WebMessage-Syntax: ${ex.message}", DefaultChannel.ERROR.name, "")
            session.remote.sendString(Gson().toJson(error_message))
        } catch (ex: Exception) {

            //Something else went wrong. Send error description to frontend.
            val error_message = WebMessage("Unknown error: ${ex.message}", DefaultChannel.ERROR.name, "")
            session.remote.sendString(Gson().toJson(error_message))
        }

    }
}