package net.cleverdesk.cleverdesk.web.handlers

import net.cleverdesk.cleverdesk.web.WebHandler
import net.cleverdesk.cleverdesk.web.WebMessage
import net.cleverdesk.cleverdesk.web.WebResponseProvider

/**
 *  Reacts on page request.
 */
class PageHandler : WebHandler {
    override val forChannels: Array<String> = arrayOf("page", "pages")

    override fun handleMessage(provider: WebResponseProvider, message: WebMessage) {
        when (message.channel) {
            "page" -> {

            }
            "pages" -> {

            }
        }
    }
}