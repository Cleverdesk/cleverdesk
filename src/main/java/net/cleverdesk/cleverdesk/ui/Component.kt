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
 * A component is UI-class which stands for a ui-element like a Label. A component could be displayed on all frontend native if they
 * support the component.
 */
interface Component {

    /**
     * The name is the identifier of component. Every name should be *unique*.
     */
    public val name: String


}