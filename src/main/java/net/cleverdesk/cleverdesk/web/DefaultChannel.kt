package net.cleverdesk.cleverdesk.web

/**
 * Created by SV on 05.10.2016.
 */
enum class DefaultChannel(channel: String) {
    ERROR("error"), INFORMATION("information"), NOTIFICATION("notification"), GEN_TOKEN("gen_token"), APPROVE_TOKEN("approve_token"), LOGOUT("logout");
}