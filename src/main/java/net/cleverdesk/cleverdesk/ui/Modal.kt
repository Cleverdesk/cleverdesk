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

/**
 * Modal is type of popup compareable to the [Bootstrap-Modal](http://getbootstrap.com/javascript/#modals)
 * @see http://getbootstrap.com/javascript/#modal
 * Supported in all frontends.
 * Mobile devices that are smaller then 5.4" will open a Modal in Full-Screen-Mode. (like [UI])
 */
@Deprecated("Will be replaced with a new Response system.")
class Modal : UI() {
    override val name: String = "Modal"
}