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
package net.cleverdesk.cleverdesk.database

import net.cleverdesk.cleverdesk.plugin.Plugin
import java.util.*

/**
 * Created by schulerlabor on 24.05.16.
 */
abstract class DatabaseObject {

    public abstract val plugin: Plugin

    public var toMap: HashMap<String, Any>
        get() {
            val fields = LinkedHashMap<String, Any>()
            for (field in this.javaClass.fields) {
                field.isAccessible = true
                if (field.isAnnotationPresent(DatabaseObject.Database::class.java)) {
                    fields.put(field.name, field.get(this))
                }

            }
            return fields
        }
        set(value) {
            for (key in value.keys) {
                val value = value.get(key)
                try {
                    val field = this.javaClass.getDeclaredField(key)
                    field.isAccessible = true
                    field.set(this, value)
                } catch(e: Exception) {
                    println("Error while restoring DBObject. Key=${key} Value=${value}")
                    return
                }

            }
        }
    public abstract val indices: HashMap<String, Any>

    annotation class Database()


}