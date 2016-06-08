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
package net.cleverdesk.cleverdesk.ui.general

import net.cleverdesk.cleverdesk.ui.Component

/**
 * A window where the user can interact with websites/HTML-elements. Theire is not a good native support! Please use the
 * ui-components of the backend and only use this in an emergency case! It is like an iFrame.
 */
class HTMLView : Component {
    override val name: String = "HTMLView"
    /**
     * The HTML-Code of the HTMLView. Please use full HTML:
     * Example:
     * ````
     * <html>
     *     <head>
     *         ...
     *     </head>
     *     <body>
     *         ...
     *     </body>
     * </html>
     * ````
     */
    var code: String = ""

}