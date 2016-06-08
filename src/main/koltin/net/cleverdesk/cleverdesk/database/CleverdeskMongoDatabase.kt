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

import com.mongodb.client.MongoDatabase
import net.cleverdesk.cleverdesk.launcher.Launcher
import net.cleverdesk.cleverdesk.plugin.Plugin
import org.bson.Document
import org.bson.types.ObjectId
import java.util.*

/**
 * @see CleverdeskMongoDatabase
 */
class CleverdeskMongoDatabase(launcher: Launcher, db: MongoDatabase) : Database<Document, String> {


    override var launcher: Launcher? = launcher
    val db: MongoDatabase = db


    override fun upload(from: DatabaseObject) {
        upload(mapToDocument(from.toMap), mapToDocument(from.indices), from.plugin, type = from.javaClass.name)
    }

    override fun upload(target: Document, indices: Document, plugin: Plugin, type: String) {
        val col = db.getCollection(plugin.description!!.name + "_" + type)
        if (download(indices, plugin, type).count() > 0) {
            col.updateOne(indices, target)
        } else {
            col.insertOne(target)
        }
    }

    override fun download(into: DatabaseObject, at: DatabaseObject) {
        download(into, mapToDocument(at.indices))
    }

    override fun download(into: DatabaseObject, at: Document) {
        val map = LinkedHashMap<String, Any?>()
        for (dbOb in download(at, into.plugin, type = into.javaClass.name)) {
            map.putAll(dbOb)
            //Only the first one should be applied!
            break
        }
        into.toMap = map
        into.index = map.get("_id") as ObjectId

    }

    override fun download(where: DatabaseObject): Array<Document> {
        if (where.indices == null) {
            return arrayOf()
        }
        return download(mapToDocument(where.indices), where.plugin, where.javaClass.name)
    }

    override fun download(indices: Document, plugin: Plugin, type: String): Array<Document> {
        if (indices == null || indices.size < 1) return arrayOf()
        val col = db.getCollection(plugin.description!!.name + "_" + type)
        val list = ArrayList<Document>()
        col.find(indices).into(list)
        return list.toTypedArray()
    }

    override fun delete(target: DatabaseObject) {
        delete(mapToDocument(target.toMap), target.plugin, target.javaClass.name)
    }

    override fun delete(indices: Document, plugin: Plugin, type: String) {
        if (indices == null || indices.size < 1) return;
        val col = db.getCollection(plugin.description!!.name + "_" + type)
        col.deleteOne(indices)
    }

    override fun defaultIndicesOf(target: DatabaseObject): Map<String, Any?>? {
        return mapOf(Pair("_id", target.index as? ObjectId))
    }

    fun mapToDocument(map: Map<String, Any?>?): Document {
        if (map == null) {
            return Document()
        }
        println(map)
        return Document(map)
    }


}