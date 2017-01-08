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
/**
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package net.cleverdesk.cleverdesk.ui

import net.cleverdesk.cleverdesk.plugin.Response
import java.util.*

/**
 * The UI bundels all UI [Component]s on a [Page]/[net.cleverdesk.cleverdesk.web.Response].
 * It is by default an Full-Screen page.
 */
@Deprecated("Will be replaced by a new Response system.")
open class UI() : ComponentGroup, Response {
    /*override fun toJson(): String {
        return Gson().toJson(this)
    }*/

    override val name: String = "UI"
    private val components: LinkedList<Component> = LinkedList<Component>()

    override fun addComponent(component: Component) {
        components.add(component)
    }

    override fun removeComponent(component: Component) {
        components.remove(component)
    }

    override fun getComponents(): List<Component> {
        return components
    }
}

