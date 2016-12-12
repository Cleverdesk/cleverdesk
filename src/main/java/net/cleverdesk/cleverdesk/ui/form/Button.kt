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
package net.cleverdesk.cleverdesk.ui.form

import net.cleverdesk.cleverdesk.listener.ListenerRegistration
import net.cleverdesk.cleverdesk.ui.Action
import net.cleverdesk.cleverdesk.ui.TextColor
import net.cleverdesk.cleverdesk.ui.events.OnClickEvent
import net.cleverdesk.cleverdesk.ui.events.OnClickListener

class Button : FormComponent() {
    override val name: String = "Button"
    var action: Action? = null
    var label: String? = null
    var type: TextColor? = null
    var onClickListener: OnClickListener?
        get() {
            fetchComponentListeners().forEach { i ->
                if (i.listener is OnClickListener) {
                    return i.listener
                }
            }
            return null
        }
        set(value) {
            registerComponentListener(ListenerRegistration(value!!, OnClickEvent::class.java))
        }

}



