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
package net.cleverdesk.cleverdesk.plugin

import net.cleverdesk.cleverdesk.UIRequest
import net.cleverdesk.cleverdesk.User

/**
 * A page is listed in the menu and is identified by the class name.
 */
interface Page {
    /**
     * The display-name of the page
     */
    public val name: String

    /**
     * @property user The user which is sending the request.
     * @property request The request and the parameters whitch are sended additionally.
     * @return The response which the backend will send to the frontend.
     * @see UI
     */
    public open fun response(user: User, request: UIRequest): Response
}
