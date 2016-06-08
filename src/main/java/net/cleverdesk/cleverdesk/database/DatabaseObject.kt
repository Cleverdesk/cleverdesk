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
import kotlin.reflect.jvm.isAccessible
import kotlin.reflect.jvm.javaField
import kotlin.reflect.jvm.properties

/**
 * Created by schulerlabor on 24.05.16.
 */
abstract class DatabaseObject {

    public abstract val plugin: Plugin

    public var toMap: Map<String, Any?>?
        get() {
            val fields = LinkedHashMap<String, Any>()
            print(this.javaClass.kotlin.members.toTypedArray())
            for (field in this.javaClass.kotlin.properties) {
                print(field.name)
                field.isAccessible = true
                if (field.annotations.find { a -> a.javaClass == Database::class.java } != null) {
                    if (field.get(this) != null) fields.put(field.name, field.get(this)!!)
                }

            }
            print(fields)
            return fields
        }
        set(value) {
            if (value == null) {
                //Nothing to restore
                return
            }
            for (key in value.keys) {
                val value = value.get(key)
                try {
                    val field = this.javaClass.kotlin.properties.find { i -> i.name == key }
                    field?.isAccessible = true
                    field?.javaField?.set(this, value)
                } catch(e: Exception) {
                    println("Error while restoring DatabaseObject. Key=${key} Value=${value}")
                    return
                }

            }
        }

    public open val indices: Map<String, Any?>?
        get() {
            return plugin.database?.defaultIndicesOf(this)
        }

    var index: Any? = null

    protected annotation class Database()


}