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
package net.cleverdesk.cleverdesk.web

import net.cleverdesk.cleverdesk.User

/**
 * An interface which react on a request by an [User] or guest.
 */
interface WebHandler {
    /**
     * The channels which will be used to communicate with the client.
     */
    val forChannels: Array<String>


    /**
     * will be called if a message in a channel in [forChannels] is called.
     * @param user Represents the requesting user and is null íf a guest requests.
     * @param provider is a provider where you could send a message to [user] or the guest.
     */
    fun handleMessage(provider: WebResponseProvider, message: WebMessage)
}