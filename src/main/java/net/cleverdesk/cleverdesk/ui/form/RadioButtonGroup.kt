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

/**
 * Created by bootslock on 26.04.16.
 */
class RadioButtonGroup : AbstractInputField<Int>(), ComponentGroup {

    private val radioButtons: LinkedList<RadioButton> = LinkedList()

    override fun removeComponent(component: Component) {
        if (component is RadioButton) {
            radioButtons.remove(component as RadioButton)
        } else {
            throw Exception("Only RadioButtons are allowed as component.")
        }
    }

    override fun getComponents(): List<Component> {
        return radioButtons
    }

    override fun addComponent(component: Component) {
        if (component is RadioButton) {
            radioButtons.add(component as RadioButton)
        } else {
            throw Exception("Only RadioButtons are allowed as component.")
        }
    }

    override var input_name: String = ""
    override val name: String = "RadioGroup"

}