package net.cleverdesk.cleverdesk.web.handlers

import net.cleverdesk.cleverdesk.launcher.Launcher
import net.cleverdesk.cleverdesk.web.DefaultChannel
import net.cleverdesk.cleverdesk.web.WebHandler
import net.cleverdesk.cleverdesk.web.WebMessage
import net.cleverdesk.cleverdesk.web.WebResponseProvider
import net.cleverdesk.cleverdesk.web.http.Authentication

/**
 * Authenticate users, logs out users, restores token.
 */
class AuthenticationHandler(launcher: Launcher) : WebHandler, Authentication(launcher) {

    override val forChannels: Array<String> = arrayOf(DefaultChannel.APPROVE_TOKEN.name, DefaultChannel.APPROVE_TOKEN.name, DefaultChannel.LOGOUT.name)

    override fun handleMessage(provider: WebResponseProvider, message: WebMessage) {
        when (message.channel) {
            DefaultChannel.LOGOUT.name -> {
                provider.user = null
                provider.sendMessage(DefaultChannel.LOGOUT.name, "success")
            }
            DefaultChannel.APPROVE_TOKEN.name -> {
                provider.user = authUser(message.message.toString())
                message.user = provider.user
                if (provider.user == null) {
                    provider.sendMessage(DefaultChannel.ERROR.name, "Your session is inactive.")
                } else {
                    provider.sendMessage(DefaultChannel.APPROVE_TOKEN.name, "success")
                }
            }
            DefaultChannel.GEN_TOKEN.name -> {
                val map = message.message as Map<String, *>
                if (map.containsKey("username") && map.containsKey("password") && map.containsKey("lifetime")) {
                    try {
                        provider.sendMessage(DefaultChannel.GEN_TOKEN.name, generateToken(map.get("username") as String, map.get("password") as String, map.get("lifetime") as Int))

                    } catch (e: Exception) {
                        provider.sendMessage(DefaultChannel.ERROR.name, e.message!!)

                    }
                } else {
                    provider.sendMessage(DefaultChannel.ERROR.name, "Invalid request")
                }

            }
        }
    }

}