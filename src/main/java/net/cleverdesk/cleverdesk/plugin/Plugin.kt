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

import net.cleverdesk.cleverdesk.database.Database
import net.cleverdesk.cleverdesk.launcher.Launcher
import net.cleverdesk.cleverdesk.listener.ListenerManager
import net.cleverdesk.cleverdesk.web.escape
import java.io.File
import java.util.*

/**
 * A plugin is an additional software module which is loaded dynamicly from the plugins/ or drivers/ folder.
 */
open class Plugin() {


    /**
     * The [Launcher] which loaded the Plugin.
     */
    public var launcher: Launcher? = null
    /**
     * TODO
     */
    public val listenerManager: ListenerManager? = launcher?.listenerManager
    /**
     * Gives a short information about the plugin. Please use [PluginInfo] to declare this informations.
     */
    public var description: PluginDescription? = null
    /**
     * The list of pages which are included in the plugin. This list is used by the launcher to build the menu.
     * @see Page
     */
    public val pages: MutableList<Page> = LinkedList<Page>()
    /**
     * The folder where the plugin can store files, local database or other stuff.
     * Example-Path (Name of the Plugin: Test) : .../plugins/Test/
     */
    public val dataDir: File ?
        get() = File(launcher?.dataFolder?.absolutePath + "${description?.name?.escape()}/")
    /**
     * Mirrors the database which is located in the launcher. Plugin-only databases are at the moment not supported and not wanted.
     */
    public val database: Database<*> ?
        get() = launcher?.database


    /**
     * Will be executed if the plugin starts up.
     */
    public open fun enable() {

    }

    /**
     * Will be executed if the plugin shuts down.
     */
    public open fun disable() {

    }

}