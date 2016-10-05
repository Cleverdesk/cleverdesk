package net.cleverdesk.cleverdesk.web.handlers

import net.cleverdesk.cleverdesk.launcher.Launcher
import net.cleverdesk.cleverdesk.plugin.PluginDescription
import net.cleverdesk.cleverdesk.web.WebHandler
import net.cleverdesk.cleverdesk.web.WebMessage
import net.cleverdesk.cleverdesk.web.WebResponseProvider
import net.cleverdesk.cleverdesk.web.http.escape
import java.util.*

/**
 *  Reacts on page request.
 */
class PageHandler(launcher: Launcher) : WebHandler {

    val launcher: Launcher = launcher;

    override val forChannels: Array<String> = arrayOf("page", "pages")

    override fun handleMessage(provider: WebResponseProvider, message: WebMessage) {
        when (message.channel) {
            "page" -> {
                val pages: HashMap<String, String> = HashMap()
                for (plugin in launcher.plugins) {
                    if (plugin.description == null) continue
                    for (page in plugin.pages) {
                        pages.put(page.name, "${(plugin.description as PluginDescription).name.escape()}/${page.name.escape()}")
                    }
                }
                message.message = pages
            }
            "pages" -> {
                /** var status_code = 404
                var response: Response? = null
                for (plugin in  launcher.plugins) {
                if (plugin.description != null &&
                (plugin.description as PluginDescription).name.escape().equals(req.params(":plugin"), true)) {
                for (page in plugin.pages) {
                if (page.name.escape().equals(req.params(":page"), true)) {

                status_code = 200
                val ui_req: UIRequest = object : UIRequest {
                override val response_target: Any
                get() = req.ip()
                override val attributes: Map<String, Any>
                get() = mapOf()

                }

                val user = auth.authUser(req.headers("token"))
                if (user == null) break;

                response = page.response(user, ui_req)
                break
                }
                }
                break
                } else {
                continue
                }

                }

                res.status(status_code)

                if (response == null) JSONResponse(status_code, "Not Found").to_json()
                else JSONResponse(status_code, response).to_json()**/
                //TODO implement Page return
            }
        }
    }
}