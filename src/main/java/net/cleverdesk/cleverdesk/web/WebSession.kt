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

import net.cleverdesk.cleverdesk.User

/**
 * WebSession is a abstraction of a session of type [T]. With WebSession multiply types of APIs could be handled.
 * Example: WebSocket or HTTP:80
 */
class WebSession<T>(session: T) {

    val session: T = session
    var user: User? = null

    val savedInSession: MutableMap<String, Any> = mutableMapOf()

    /**
     * Save [value] in this [WebSession] by [key]. This data will be lost on memory clear or restart.
     */
    fun save(key: String, value: Any) {
        savedInSession.put(key, value)
    }

    /**
     * Restores [key] that was saved by the [save]-method.
     */
    fun restore(key: String): Any? {
        return savedInSession.get(key)
    }


}