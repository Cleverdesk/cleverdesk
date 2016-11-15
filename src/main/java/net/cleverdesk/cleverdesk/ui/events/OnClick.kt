package net.cleverdesk.cleverdesk.ui.events

import net.cleverdesk.cleverdesk.User
import net.cleverdesk.cleverdesk.listener.Event
import net.cleverdesk.cleverdesk.listener.Listener
import net.cleverdesk.cleverdesk.ui.IdentifiableComponent

/**
 *
 */
class OnClickEvent : Event {
    /**
     * The user who triggered the event. The property is null if the request was sent from an anonymous user.
     * @see
     */
    override val trigger: User? = null

    /**
     * The component clicked by the [trigger]
     * @see
     */
    val clickedComponent: IdentifiableComponent? = null

    /**
     * Applies the [map] to the [OnClickEvent]
     *
     * [trigger] = type UUID; key: trigger
     * [clickedComponent] = type UUID, key: clicked
     */
    override fun deserialize(map: Map<String, Object>) {
        throw UnsupportedOperationException()
    }


}


interface OnClickListener : Listener<OnClickEvent> {}