package net.cleverdesk.cleverdesk.web.handlers

import net.cleverdesk.cleverdesk.UIRequest
import net.cleverdesk.cleverdesk.launcher.Launcher
import net.cleverdesk.cleverdesk.plugin.PluginDescription
import net.cleverdesk.cleverdesk.web.DefaultChannel
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
            "pages" -> {
                val pages: HashMap<String, String> = HashMap()
                for (plugin in launcher.plugins) {
                    if (plugin.description == null) continue
                    for (page in plugin.pages) {
                        pages.put(page.name, "${(plugin.description as PluginDescription).name.escape()}/${page.name.escape()}")
                    }
                }
                provider.sendMessage("pages", pages)
            }
            "page" -> {
                val map = message.message as Map<String, String>

                val page = map.get("page")
                val plugin = map.get("plugin");
                for (plugin in launcher.plugins) {
                    if (plugin.description != null && (plugin.description as PluginDescription).name.escape().equals(page, true)) {
                        for (page in plugin.pages) {
                            if (page.name.escape().equals(page)) {
                                val ui_req: UIRequest = object : UIRequest {
                                    override val attributes: Map<String, Any> = mapOf()
                                    override val response_target: Any = provider

                                }
                                //provider.sendMessage("page", page.response(message.user, ui_req))
                                return;
                            }
                        }
                    }

                }
                provider.sendMessage(DefaultChannel.ERROR.name, "Page not found")
            }
        }
    }
}