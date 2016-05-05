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

package net.cleverdesk.cleverdesk.launcher

import net.cleverdesk.cleverdesk.listener.Listener
import net.cleverdesk.cleverdesk.listener.ListenerManager
import net.cleverdesk.cleverdesk.plugin.Plugin
import net.cleverdesk.cleverdesk.web.WebServer
import spark.Spark
import java.io.File
import java.util.*
import java.util.logging.Logger

class Launcher {

    public val plugins: MutableList<Plugin> = LinkedList<Plugin>()

    companion object {
        val LOG = Logger.getLogger(Launcher.javaClass.name)
    }
    public val listenerManager: ListenerManager = object : LinkedList<Listener>(), ListenerManager {}


    public val dataFolder: File
        get() {
            return File("${Launcher::class.java.protectionDomain.codeSource.location.toURI().path.replace(".jar", "")}/")
        }

    public fun start() {
        LOG.info("License GPLv3+: GNU GPL version 3 or later <http://gnu.org/licenses/gpl.html>")
        LOG.info("This is free software: you are free to change and redistribute it.")
        LOG.info("There is NO WARRANTY, to the extent permitted by law.")
        Runtime.getRuntime().addShutdownHook(Thread(Runnable { shutdown() }))
        if (!dataFolder.exists()) dataFolder.mkdirs()
        if (!File(dataFolder, "plugins/").exists()) File(dataFolder, "plugins/").mkdir()
        for (plugin in plugins) {
            plugin.enable()
        }
        var port = System.getenv("PORT")
        if (port == null) {
            port = System.getenv("port")
            if (port == null) {
                port = "8080"
            }
        }

        LOG.info("Starting WebServer on Port ${port}")

        WebServer.start(this, port.toInt())

    }

    public fun shutdown() {
        for (plugin in plugins) {
            plugin.disable()
        }
        Spark.stop()
        LOG.info("Backend stopped")

    }
}