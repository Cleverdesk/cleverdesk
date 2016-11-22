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
package net.cleverdesk.cleverdesk.ui.events

import net.cleverdesk.cleverdesk.User
import net.cleverdesk.cleverdesk.listener.Event
import net.cleverdesk.cleverdesk.listener.Listener
import net.cleverdesk.cleverdesk.ui.IdentifiableComponent

/**
 *
 */
class OnClickEvent : Event {
    /**
     * The user who triggered the event. The property is null if the request was sent from an anonymous user.
     * @see
     */
    override val trigger: User? = null

    /**
     * The component clicked by the [trigger]
     * @see
     */
    val clickedComponent: IdentifiableComponent? = null

    /**
     * Applies the [map] to the [OnClickEvent]
     *
     * [trigger] = type UUID; key: trigger
     * [clickedComponent] = type UUID, key: clicked
     */
    override fun deserialize(map: Map<String, Object>) {
        throw UnsupportedOperationException()
    }


}


interface OnClickListener : Listener<OnClickEvent> {}