package net.cleverdesk.cleverdesk.web

import net.cleverdesk.cleverdesk.User


class WebMessage {
    public open var user: User? = null
    public open var message: Any = ""
    public open var channel: String = ""
}