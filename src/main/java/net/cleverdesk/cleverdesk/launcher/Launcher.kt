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

import net.cleverdesk.cleverdesk.database.Database
import net.cleverdesk.cleverdesk.database.drivers.MongoDriver
import net.cleverdesk.cleverdesk.listener.Listener
import net.cleverdesk.cleverdesk.listener.ListenerManager
import net.cleverdesk.cleverdesk.plugin.Plugin
import net.cleverdesk.cleverdesk.plugin.PluginDescription
import net.cleverdesk.cleverdesk.plugin.PluginLoader
import net.cleverdesk.cleverdesk.web.WebServer
import spark.Spark
import java.io.File
import java.util.*

class Launcher : Plugin() {


    public val plugins: MutableList<Plugin> = LinkedList<Plugin>()

    public override var database: Database<*, *>? = null


    public override val listenerManager: ListenerManager = object : LinkedList<Listener>(), ListenerManager {}

    /**
     * The folder, where all data (like plugins) are.
     */
    public val dataFolder: File
        get() {
            return File("${Launcher::class.java.protectionDomain.codeSource.location.toURI().path.replace(".jar", "")}/")
        }

    public override fun enable() {
        description = object : PluginDescription {
            override val name: String = "Cleverdesk Launcher"
            override val description: String = "The cleverdesk launcher that loads plugins and manage data."
            override val author: String = "Cleverdesk Team"

        }
        println("License GPLv3+: GNU GPL version 3 or later <http://gnu.org/licenses/gpl.html>")
        println("This is free software: you are free to change and redistribute it.")
        println("There is NO WARRANTY, to the extent permitted by law.")

        //On ^C execute shutdown()
        Runtime.getRuntime().addShutdownHook(Thread(Runnable { disable() }))

        //Create Folders (plugins etc.)
        if (!dataFolder.exists()) dataFolder.mkdirs()
        if (!File(dataFolder, "plugins/").exists()) File(dataFolder, "plugins/").mkdir()
        if (!File(dataFolder, "drivers/").exists()) File(dataFolder, "drivers/").mkdir()


        //Be sure that theire is no duplicate
        for (plugin in plugins) {
            plugin.disable()
        }
        plugins.clear()
        //Loading Plugins from drivers/
        plugins.addAll(PluginLoader().loadPlugins(this, "drivers"))
        if (plugins.count() < 1) {
            //If their is no db-driver loaded load the default driver "Mongo"!
            val defaultDriver = MongoDriver()
            defaultDriver.launcher = this
            defaultDriver.description = object : PluginDescription {
                override val name: String = "MongoDB Database Driver"
                override val description: String = "The default database driver. Replace it by placing your driver in drivers/!"
                override val author: String = "Cleverdesk"

            }
            plugins.add(defaultDriver)
        }
        //Loading Plugins from plugins/*.jar
        plugins.addAll(PluginLoader().loadPlugins(this))
        //Enabling all plugins
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

        println("Starting WebServer on Port ${port}")
        //Start Web-Server
        WebServer.start(this, port.toInt())

    }

    public override fun disable() {
        for (plugin in plugins) {
            plugin.disable()
        }
        println("Backend stopped")
        Spark.stop()

    }
}