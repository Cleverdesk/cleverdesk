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

import net.cleverdesk.cleverdesk.launcher.Launcher
import net.cleverdesk.cleverdesk.plugin.Plugin

/**
 * * A part of the Database-API
 *
 * * This interface is used to use any database-type (for example MongoDB or MySQL). The database-drivers should implement their
 * database via this interface.
 * * Every database should also work with [DatabaseObject]-Instances.
 *
 * @param S The alternative or original data-transport-type like BasicDBObject (Mongo)
 * @sample MongoDatabase
 *
 */
interface Database<S> {

    var launcher: Launcher?

    /**
     * Inserts or updates a [from]
     */
    public fun upload(from: DatabaseObject)
    public fun upload(target: S, indices: S, plugin: Plugin)

    /**
     * Queries a [into] and saves the result in the [at] Object
     */
    public fun download(into: DatabaseObject, at: DatabaseObject)
    public fun download(into: DatabaseObject, at: S)
    public fun download(where: DatabaseObject): Array<S>
    public fun download(indices: S, plugin: Plugin): Array<S>


    /**
     * Deletes [target] from the DB
     */
    public fun delete(target: DatabaseObject)
    public fun delete(indices: S, plugin: Plugin)

    /**
     * @property target The database-implementation should look at a existing index in [target]
     * @return The default indices of [target] like _id in [MongoDatabase]
     */
    public fun defaultIndicesOf(target: DatabaseObject) : Map<String, Any?>?


}