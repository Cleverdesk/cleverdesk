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
import kotlin.reflect.KMutableProperty
import kotlin.reflect.jvm.isAccessible
import kotlin.reflect.jvm.properties

/**
 * Created by schulerlabor on 24.05.16.
 */
abstract class DatabaseObject {

    public abstract val plugin: Plugin

    public var toMap: Map<String, Any?>?
        /**
         * Generates a Map by Kotlin Refelection
         */
        get() {
            val fields = LinkedHashMap<String, Any>()
            for (field in this.javaClass.kotlin.members) {
                // println(field.name)
                field.isAccessible = true
                if (field.annotations.find({ a -> a.annotationClass.qualifiedName?.equals(Database::class.qualifiedName)!! }) != null) {

                    try {
                        if (field.call(this) != null) fields.put(field.name, field.call(this)!!)
                    } catch (e: Exception) {
                        //To few arguments passed! A mistake by the developer!
                        println(e.message)

                    }
                }

            }
            return fields
        }
        /**
         * Inserts [value] into the fields that provided in the map.
         */
        set(value) {
            if (value == null) {
                //Nothing to restore
                return
            }
            for (key in value.keys) {
                val value = value.get(key)
                try {
                    var prop = this.javaClass.kotlin.properties.find { i -> i.name == key } as KMutableProperty<*>?
                    prop?.isAccessible = true
                    prop?.setter?.call(this, value)
                } catch(e: Exception) {
                    e.printStackTrace()
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

    public annotation class Database()


}