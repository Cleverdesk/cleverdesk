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
import net.cleverdesk.cleverdesk.plugin.Page
import net.cleverdesk.cleverdesk.plugin.Plugin
import net.cleverdesk.cleverdesk.plugin.Response
import net.cleverdesk.cleverdesk.ui.Alert
import net.cleverdesk.cleverdesk.ui.UI
import net.cleverdesk.cleverdesk.ui.form.InputField

/**
 * Created by schulerlabor on 22.06.16.
 */
class Configuration(override val plugin: Plugin, override val name: String) : DatabaseObject(), Page {


    public fun refreshContent() {
        plugin.database?.download(this, this)
        plugin.database?.upload(this)
    }

    @Database
    public var content: MutableMap<String, String?> = mutableMapOf()

    @Database
    public val identifer: String = name


    override val indices: Map<String, Any?>?
        get() = mutableMapOf(Pair("identifer", identifer))


    override fun response(user: User, request: UIRequest): Response {

        if (request.attributes.size > 0) {
            refreshContent()
            content.putAll(request.attributes as Map<out String, String?>)
            plugin.database?.upload(this)
            refreshContent()
            return Alert("Saved")
        }

        refreshContent()
        val ui = UI()
        for (key in content.keys) {
            val field = InputField()
            field.label = key
            field.value = content.get(key)
            field.identifer = key
            ui.addComponent(field)
        }
        return ui
    }
}