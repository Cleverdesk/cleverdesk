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
package net.cleverdesk.cleverdesk.database

import net.cleverdesk.cleverdesk.launcher.Launcher
import net.cleverdesk.cleverdesk.plugin.Plugin

/**
 * Created by schulerlabor on 24.05.16.
 */
interface Database<S> {

    var launcher: Launcher?

    public fun upload(from: DatabaseObject)
    public fun upload(target: S, indices: S, plugin: Plugin)


    public fun download(into: DatabaseObject, at: DatabaseObject)
    public fun download(into: DatabaseObject, at: S)
    public fun download(where: DatabaseObject): Array<S>
    public fun download(indices: S, plugin: Plugin): Array<S>

    public fun delete(target: DatabaseObject)
    public fun delete(indices: S, plugin: Plugin)


}