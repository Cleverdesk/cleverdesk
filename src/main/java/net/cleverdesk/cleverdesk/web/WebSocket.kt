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
