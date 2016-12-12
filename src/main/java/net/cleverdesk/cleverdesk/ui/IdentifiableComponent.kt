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
package net.cleverdesk.cleverdesk.ui

import net.cleverdesk.cleverdesk.listener.ListenerManager
import net.cleverdesk.cleverdesk.listener.ListenerRegistration
import net.cleverdesk.cleverdesk.plugin.Response
import java.util.*


abstract class IdentifiableComponent : Component {
    public var identifer: String = UUID.randomUUID().toString()
    public var additionalResponse: Response? = null


    private var listeners: MutableMap<String, ListenerRegistration<*>> = mutableMapOf()


    /**
     * Registers [registration] as a component-wide listener. A component-wide listener will be called until the component is send to
     * the client. The listener is nassacary if the [UI] is already sent to the user.
     */
    public fun registerComponentListener(registration: ListenerRegistration<*>) {
        listeners.put(registration.event_type.canonicalName, registration)
    }

    /**
     * Unregisters [registration] from component-wide-[listeners].
     */
    public fun unregisterComponentListener(registration: ListenerRegistration<*>) {
        listeners.remove(registration.event_type.canonicalName)
    }

    /**
     * @return The [ListenerManager] of all component-wide registered listeners.
     * @see registerComponentListener
     */
    public fun fetchComponentListeners(): ListenerManager {

        return object : ListenerManager, LinkedList<ListenerRegistration<*>>(listeners.values) {}
    }
}