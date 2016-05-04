import net.cleverdesk.cleverdesk.UIRequest
import net.cleverdesk.cleverdesk.User
import net.cleverdesk.cleverdesk.plugin.Page
import net.cleverdesk.cleverdesk.plugin.Plugin
import net.cleverdesk.cleverdesk.plugin.PluginInfo
import net.cleverdesk.cleverdesk.ui.UI
import net.cleverdesk.cleverdesk.ui.form.InputField

/**
 * Created by jonas on 04.05.16.
 */
@PluginInfo(author = "Jonas", name = "Test-Plugin", description = "A cool Test-Plugin with many features! ")
class PluginExample : Plugin() {
    override fun enable() {
        pages + CoolPage()
    }

    override fun disable() {

    }
}

class CoolPage : Page {
    override fun response(user: User, request: UIRequest): UI {
        val ui: UI = UI("Coole Seiter")
        val vorname: InputField = InputField()
        vorname.max = 20
        vorname.input_name = "vorname"
        ui.addComponent(vorname)
        return ui
    }

}