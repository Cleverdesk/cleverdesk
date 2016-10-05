package net.cleverdesk.cleverdesk.web

import net.cleverdesk.cleverdesk.User
import java.util.*


class WebMessage {
    public open var user: User? = null
    public open var message: Any = ""
    public open var channel: String = ""
    public open var request_id: String = UUID.randomUUID().toString()
}