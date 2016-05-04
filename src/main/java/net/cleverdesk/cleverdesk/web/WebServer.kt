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
package net.cleverdesk.cleverdesk.web

/**
 * Created by jkuche on 23.04.16.
 */


import net.cleverdesk.cleverdesk.UIRequest
import net.cleverdesk.cleverdesk.User
import net.cleverdesk.cleverdesk.launcher.Launcher
import net.cleverdesk.cleverdesk.plugin.PluginDescription
import net.cleverdesk.cleverdesk.ui.UI
import spark.Spark.get
import spark.Spark.port
import java.util.*

object WebServer {
    @JvmStatic fun start(launcher: Launcher) {

        var port = System.getenv("PORT")
        if (port == null) {
            port = System.getenv("port")
            if (port == null) {
                port = "8080"
            }
        }
        port(Integer.parseInt(port))
        get("/", { req, res ->
            Response(200, "Welcome to Cleverdesk").to_json()
        })
        get("/pages", { req, res ->

            val pages: HashMap<String, String> = HashMap()

            for (plugin in  launcher.plugins) {
                if (plugin.description == null) continue
                for (page in plugin.pages) {
                    pages.put(page.name, "${(plugin.description as PluginDescription).name.escape()}/${page.name.escape()}")
                }
            }

            Response(200, pages).to_json()
        })
        get(":plugin/:page", { req, res ->

            var status_code = 404
            var response: UI? = null
            for (plugin in  launcher.plugins) {
                if (plugin.description != null &&
                        (plugin.description as PluginDescription).name.escape().equals(req.params(":plugin"), true)) {
                    for (page in plugin.pages) {
                        if (page.name.escape().equals(req.params(":page"), true)) {

                            status_code = 200
                            val ui_req = object : UIRequest {
                                override val response_target: Any
                                    get() = req.ip()
                                override val attributes: Map<String, Any>
                                    get() = req.queryMap().toMap()

                            }
                            val user = User()
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

            if (response == null) Response(status_code, "Not Found").to_json()
            else Response(status_code, response).to_json()
        })
        get("*", { req, res ->
            res.status(404)
            Response(404, "Not Found").to_json()
        })
    }


}

fun String.escape(): String {
    //TODO Implement escape
    return this
}