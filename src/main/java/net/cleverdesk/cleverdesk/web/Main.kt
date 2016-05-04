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


import net.cleverdesk.cleverdesk.launcher.Launcher
import spark.Spark.*
object Main {
    @JvmStatic fun main(launcher:Launcher) {
        var port = System.getenv("PORT")
        if (port == null) {
            port = System.getenv("port")
            if (port == null) {
                port = "8080"
            }
        }
        port(Integer.parseInt(port))
        get("/", { req, res->
            Response(200, "Welcome to Cleverdesk").to_json()
        })
        get("/pages", { req, res ->
            Response(200, launcher.plugins).to_json()
        })
        get("*", { req, res->
            res.status(404)
            Response(404, "Not Found").to_json()
        })
    }
}