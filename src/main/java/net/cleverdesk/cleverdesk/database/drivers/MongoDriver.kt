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
package net.cleverdesk.cleverdesk.database.drivers

import com.mongodb.MongoClient
import com.mongodb.client.MongoDatabase
import net.cleverdesk.cleverdesk.database.CleverdeskMongoDatabase
import net.cleverdesk.cleverdesk.plugin.Plugin

/**
 * Created by schulerlabor on 01.06.16.
 */

public class MongoDriver() : Plugin() {

    private var db: MongoDatabase = MongoClient("localhost").getDatabase("cleverdesk")

    override fun enable() {
        launcher?.database = CleverdeskMongoDatabase(launcher!!, db)
    }

}