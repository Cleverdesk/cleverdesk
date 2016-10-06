package net.cleverdesk.cleverdesk.web

import net.cleverdesk.cleverdesk.User

/**
 * An interface which react on a request by an [User] or guest.
 */
interface WebHandler {
    /**
     * The channels which will be used to communicate with the client.
     */
    val forChannels: Array<String>


    /**
     * will be called if a message in a channel in [forChannels] is called.
     * @param user Represents the requesting user and is null íf a guest requests.
     * @param provider is a provider where you could send a message to [user] or the guest.
     */
    fun handleMessage(provider: WebResponseProvider, message: WebMessage)
}