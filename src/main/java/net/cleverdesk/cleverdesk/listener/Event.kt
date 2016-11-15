package net.cleverdesk.cleverdesk.listener


/**
 * An event contains data to handle by a listener implementation
 */
interface Event {
    /**
     * Every event must have a trigger it could be for example a user.
     */
    val trigger: Any?

    fun deserialize(map: Map<String, Object>)
}