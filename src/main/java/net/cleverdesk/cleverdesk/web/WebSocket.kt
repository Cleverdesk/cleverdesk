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

import net.cleverdesk.cleverdesk.launcher.Launcher
import spark.Spark
import spark.Spark.*


object WebSocket {
    public open val handlerManager = WebHandlerManager()
    /**
     * Starts the websocket and a static file server.
     * Path for websocket: /ws
     * Path for static files: /static
     * @param port Port of websocket / static file server (sfs)
     */
    open fun start(launcher: Launcher, port: Int) {
        //Using [port].
        Spark.port(port)
        //Create Spark-Websocket under path /ws
        webSocket("/ws", WebSocketServer::class.java)
        staticFileLocation("/static")
        init()
    }
}
