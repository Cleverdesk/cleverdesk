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

import net.cleverdesk.cleverdesk.ui.Component
import net.cleverdesk.cleverdesk.ui.ComponentGroup
import java.util.*

class Form : ComponentGroup {

    private val fields: MutableList<String> = mutableListOf()

    override val name: String = "Form"

    var identifer: String = UUID.randomUUID().toString()

    private val components: LinkedList<Component> = LinkedList<Component>()

    override fun addComponent(component: Component) {
        components.add(component)
        if (component is AbstractInputField<*>) {
            fields.add(component.identifer)
        }
    }

    override fun removeComponent(component: Component) {
        components.remove(component)
        if (component is AbstractInputField<*>) {
            fields.remove(component.identifer)
        }
    }


    override fun getComponents(): List<Component> {
        return components
    }



}