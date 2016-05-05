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
package net.cleverdesk.cleverdesk.ui.general

import net.cleverdesk.cleverdesk.ui.Component
import net.cleverdesk.cleverdesk.ui.ComponentGroup

import java.util.*

/**
 * Created by matthias on 03.05.16.
 */
class ImageSlideShow() : ComponentGroup {

    override val name: String = "ImageSlideShow"

    
    private val images: LinkedList<Image> = LinkedList()

    override fun removeComponent(component: Component) {
        if (component is Image) {
            images.remove(component as Image)
        } else {
            throw Exception("Only Images are allowed as component.")
        }
    }

    override fun getComponents(): List<Component> {
        return images
    }

    override fun addComponent(component: Component) {
        if (component is Image) {
            images.add(component as Image)
        } else {
            throw Exception("Only Images are allowed as component.")
        }
    }
}