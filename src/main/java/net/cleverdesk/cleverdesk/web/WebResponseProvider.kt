package net.cleverdesk.cleverdesk.web

import net.cleverdesk.cleverdesk.User

/**
 * Created by SV on 05.10.2016.
 */
interface WebResponseProvider {
    fun sendMessage(channel: String, message: String)
    fun sendMessage(channel: String, message: Any)
    fun saveInSession(key: String, value: Any)
    var user: User?
}