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

package net.cleverdesk.cleverdesk.launcher

import net.cleverdesk.cleverdesk.listener.Listener
import net.cleverdesk.cleverdesk.listener.ListenerManager
import net.cleverdesk.cleverdesk.plugin.Plugin
import java.util.*

class Launcher {

    public val plugins: MutableList<Plugin> = LinkedList<Plugin>()
    public val listenerManager: ListenerManager = object : LinkedList<Listener>(), ListenerManager {}

    public fun start() {
        for (plugin in plugins) {
            plugin.enable()
        }
    }

    public fun shutdown() {
        for (plugin in plugins) {
            plugin.disable()
        }

    }
}