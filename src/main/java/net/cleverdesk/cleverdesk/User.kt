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
package net.cleverdesk.cleverdesk

import net.cleverdesk.cleverdesk.database.DatabaseObject
import net.cleverdesk.cleverdesk.plugin.Plugin
import java.util.*

/**
 * TODO
 */
open class User(override val plugin: Plugin) : DatabaseObject() {
    @Database
    public open var first_name: String? = null
    @Database
    public open var last_name: String? = null
    @Database
    public open var username: String? = null
    @Database
    public open var password: String? = null
    @Database
    public open var external_data: Map<String, Any> = mapOf()
    @Database
    public open var uuid: String = UUID.randomUUID().toString()


    public fun hasPermission(permission: String): Boolean {
        //TODO Implement access and permission system!
        print(username)
        return true
    }

    init {
        print(toMap)
    }

    override val indices: Map<String, Any>
        get() {
            if (username != null) return mapOf(Pair("username", username!!))
            return mapOf(Pair("uuid", uuid))
        }


}