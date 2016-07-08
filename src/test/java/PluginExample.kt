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
import net.cleverdesk.cleverdesk.UIRequest
import net.cleverdesk.cleverdesk.User
import net.cleverdesk.cleverdesk.plugin.Page
import net.cleverdesk.cleverdesk.plugin.Plugin
import net.cleverdesk.cleverdesk.plugin.PluginDescription
import net.cleverdesk.cleverdesk.plugin.PluginInfo
import net.cleverdesk.cleverdesk.ui.UI
import net.cleverdesk.cleverdesk.ui.form.InputField

/**
 * Created by jonas on 04.05.16.
 */
@PluginInfo(author = "Jonas", name = "Test-Plugin", description = "A cool Test-Plugin with many features! ")
class PluginExample : Plugin() {

    override fun enable() {
        description = object : PluginDescription {
            override val name: String = "cool_plugin"
            override val description: String = "Cool!"
            override val author: String = "Jonas"

        }
        pages.add(CoolPage())
    }

    override fun disable() {

    }
}

class CoolPage() : Page {
    override val name: String = "coolpage"

    override fun response(user: User, request: UIRequest): UI {
        val ui: UI = UI()
        val vorname: InputField = InputField()
        vorname.max = 20
        vorname.identifer = "vorname"
        ui.addComponent(vorname)
        return ui
    }

}