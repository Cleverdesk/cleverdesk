package net.cleverdesk.cleverdesk.web

/**
 * Created by SV on 05.10.2016.
 */
interface WebResponseProvider {
    fun sendMessage(channel: String, message: String)
    fun sendMessage(channel: String, message: Any)
}