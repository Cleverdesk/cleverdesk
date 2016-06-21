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


import com.google.gson.Gson
import net.cleverdesk.cleverdesk.BuildProperties
import net.cleverdesk.cleverdesk.UIRequest
import net.cleverdesk.cleverdesk.User
import net.cleverdesk.cleverdesk.launcher.Launcher
import net.cleverdesk.cleverdesk.plugin.PluginDescription
import net.cleverdesk.cleverdesk.ui.UI
import spark.Request
import spark.Spark.*
import java.util.*

object WebServer {
    @JvmStatic fun start(launcher: Launcher, port: Int) {
        val auth = Authentication(launcher)
        port(port)
        get("/", { req, res ->
            Response(200, BuildProperties.VERSION + "/" + BuildProperties.TYPE).to_json()
        })
        get("/pages", { req, res ->
            if (checkAccess("access", req, auth)) {
                val pages: HashMap<String, String> = HashMap()

                for (plugin in  launcher.plugins) {
                    if (plugin.description == null) continue
                    for (page in plugin.pages) {
                        pages.put(page.name, "${(plugin.description as PluginDescription).name.escape()}/${page.name.escape()}")
                    }
                }

                Response(200, pages).to_json()
            } else {
                Response(403, "Access denied").to_json()
            }


        })
        post("/auth") { req, res ->
            try {
                val authReq = Gson().fromJson(req.body(), AuthRequest::class.java)
                if (authReq.username == null || authReq.password == null) {
                    res.status(400)
                    Response(400, "Invalid arguments").to_json()
                } else {
                    try {
                        Response(200, auth.generateToken(authReq.username!!, authReq.password!!)).to_json()
                    } catch(e: AuthenticationException) {
                        Response(403, e.message!!).to_json()
                    }

                }
            } catch(ex: Exception) {
                ex.printStackTrace()
                Response(400, "Invalid arguments").to_json()

            }

        }
        get(":plugin/:page", { req, res ->
            if (checkAccess(req.params(":plugin") + "." + req.params(":page"), req, auth)) {
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
                                val user = User(plugin)
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
            } else {
                Response(403, "Access denied").to_json()
            }

        })
        get("*", { req, res ->
            res.status(404)
            Response(404, "Not Found").to_json()
        })
    }

    private fun checkAccess(permission: String, req: Request, auth: Authentication): Boolean {
        if (req.headers("token") == null) {
            return false
        } else {
            try {
                val user = auth.authUser(req.headers("token"))
                return user.hasPermission(permission)
            } catch(ex: AuthenticationException) {
                return false
            }
        }
    }


}

fun String.escape(): String {
    //TODO Implement escape
    return this
}