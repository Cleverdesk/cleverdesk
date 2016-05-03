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