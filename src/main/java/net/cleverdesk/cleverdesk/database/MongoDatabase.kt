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

import com.mongodb.BasicDBObject
import com.mongodb.DB
import com.mongodb.DBObject
import net.cleverdesk.cleverdesk.launcher.Launcher
import net.cleverdesk.cleverdesk.plugin.Plugin
import org.bson.types.ObjectId
import java.util.*

/**
 * @see MongoDatabase
 */
class MongoDatabase(launcher: Launcher, db: DB) : Database<DBObject> {


    override var launcher: Launcher? = launcher
    val db: DB = db


    override fun upload(from: DatabaseObject) {
        upload(mapToDBObject(from.toMap), mapToDBObject(from.indices), from.plugin)
    }

    override fun upload(target: DBObject, indices: DBObject, plugin: Plugin) {
        val col = db.getCollection(plugin.description!!.name)
        if (download(indices, plugin).count() > 0) {
            col.update(indices, target)
        } else {
            col.insert(target)
        }
    }

    override fun download(into: DatabaseObject, at: DatabaseObject) {
        download(into, mapToDBObject(at.indices))
    }

    override fun download(into: DatabaseObject, at: DBObject) {
        val map = LinkedHashMap<String, Any?>()
        for (dbOb in download(at, into.plugin)) {
            map.putAll(dbOb.toMap() as Map<out String, Any?>)
            //Only the first one should be applied!
            break
        }
        into.toMap = map
        into.index = map.get("_id") as ObjectId

    }

    override fun download(where: DatabaseObject): Array<DBObject> {
        if (where.indices == null) {
            return arrayOf()
        }
        return download(mapToDBObject(where.indices), where.plugin)
    }

    override fun download(indices: DBObject, plugin: Plugin): Array<DBObject> {
        if (indices == null || indices.toMap().size < 1) return arrayOf()
        val col = db.getCollection(plugin.description!!.name)
        return col.find(indices).toArray().toTypedArray()
    }

    override fun delete(target: DatabaseObject) {
        delete(mapToDBObject(target.toMap), target.plugin)
    }

    override fun delete(indices: DBObject, plugin: Plugin) {
        if (indices == null || indices.toMap().size < 1) return;
        val col = db.getCollection(plugin.description!!.name)
        col.remove(indices)
    }

    override fun defaultIndicesOf(target: DatabaseObject): Map<String, Any?>? {
        return mapOf(Pair("_id", target.index as? ObjectId))
    }

    fun mapToDBObject(map: Map<String, Any?>?): DBObject {
        if (map == null) {
            return BasicDBObject()
        }
        return BasicDBObject(map)
    }


}