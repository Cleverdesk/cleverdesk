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
package net.cleverdesk.cleverdesk.plugin

import net.cleverdesk.cleverdesk.launcher.Launcher
import java.io.File
import java.io.FilenameFilter
import java.net.URL
import java.net.URLClassLoader
import java.util.*
import java.util.jar.JarEntry
import java.util.jar.JarFile


class PluginLoader {
    public fun loadPlugins(launcher: Launcher): List<Plugin> {
        val plugin_folder = File(launcher.dataFolder, "plugins/")
        println("Loading plugins from ${plugin_folder.absolutePath}")

        val plugins: MutableList<Plugin> = LinkedList<Plugin>()

        for (jar_name: String in plugin_folder.list(FilenameFilter { file, name -> name.endsWith(".jar", true) })) {
            println("Found ${jar_name}")
            val jar: JarFile = JarFile(File(plugin_folder, jar_name))
            val entries: Enumeration<JarEntry> = jar.entries()
            val urls: Array<URL> = arrayOf(URL("jar:file:${File(plugin_folder, jar_name).absolutePath}!/"))
            println("-> ${urls}")
            val cl: URLClassLoader = URLClassLoader.newInstance(urls)

            val classes: HashMap<String, Class<*>> = LinkedHashMap();

            var plugin: Plugin? = null

            while (entries.hasMoreElements()) {
                val entry: JarEntry = entries.nextElement()

                if (entry.isDirectory || !entry.name.endsWith(".class")) {
                    continue
                }

                var className = entry.name.substring(0, entry.name.length - 6)
                className = className.replace('/', '.')
                val c: Class<*> = cl.loadClass(className)
                classes.put(className, c)

                println("Loading: ${c.name}")

                if (Plugin::class.java.isAssignableFrom(c)) {
                    if (plugin != null) {
                        continue
                    }

                    println("Found Plugin-Instance: ${c.name}")
                    plugin = c.newInstance() as Plugin
                }

                if (plugin == null) {
                    continue
                }

                var plInfo: PluginInfo? = null

                for (anno in plugin.javaClass.annotations) {
                    if (anno is PluginInfo) {
                        plInfo = anno as PluginInfo
                    }

                }

                if (plInfo == null) {

                    println("Error can't find @PluginInfo")
                    continue
                }

                plugin!!.description = object : PluginDescription {
                    override val name: String
                        get() = (plInfo as PluginInfo).name
                    override val description: String
                        get() = (plInfo as PluginInfo).description
                    override val author: String
                        get() = (plInfo as PluginInfo).author
                }

                println("Loaded ${(plugin.description as PluginDescription).name}")

                plugins.add(plugin)

            }


        }
        return plugins
    }
}