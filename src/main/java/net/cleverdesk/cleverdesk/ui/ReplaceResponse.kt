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

import net.cleverdesk.cleverdesk.plugin.Response


class ReplaceResponse : Response {
    override val name: String = "Replace"
    private val components: MutableMap<String, ReplaceableComponent> = mutableMapOf()

    public fun replace(component: ReplaceableComponent, with: ReplaceableComponent) {
        components.put(component.identifer, with)
    }
}
