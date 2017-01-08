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
package net.cleverdesk.cleverdesk.web.handlers

import net.cleverdesk.cleverdesk.UIRequest
import net.cleverdesk.cleverdesk.launcher.Launcher
import net.cleverdesk.cleverdesk.plugin.PluginDescription
import net.cleverdesk.cleverdesk.web.*
import net.cleverdesk.cleverdesk.web.http.escape
import java.util.*

/**
 *  Reacts on page request.
 */
class PageHandler(launcher: Launcher) : WebHandler {

    val launcher: Launcher = launcher;

    override val forChannels: Array<String> = arrayOf("page", "pages")

    override fun handleMessage(provider: WebResponseProvider, inSession: WebSession<*>, message: WebMessage) {
        when (message.channel) {
        //All registered pages
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
        //A specific page
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
                                    override val response_target: Any = inSession
                                }
                                provider.sendMessage("page", page.response(inSession.user, ui_req))
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