/**
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package net.cleverdesk.cleverdesk.web.handlers

import net.cleverdesk.cleverdesk.launcher.Launcher
import net.cleverdesk.cleverdesk.web.*
import net.cleverdesk.cleverdesk.web.http.Authentication

/**
 * Authenticate users, logs out users, restores token.
 */
class AuthenticationHandler(launcher: Launcher) : WebHandler, Authentication(launcher) {

    override val forChannels: Array<String> = arrayOf(DefaultChannel.APPROVE_TOKEN.name, DefaultChannel.APPROVE_TOKEN.name, DefaultChannel.LOGOUT.name, DefaultChannel.GEN_TOKEN.name)

    override fun handleMessage(provider: WebResponseProvider, inSession: WebSession<*>, message: WebMessage) {
        when (message.channel) {
            DefaultChannel.LOGOUT.name -> {
                inSession.user = null
                provider.sendMessage(DefaultChannel.LOGOUT.name, "success")
            }

            DefaultChannel.APPROVE_TOKEN.name -> {
                inSession.user = authUser(message.message.toString())
                //message.user = provider.user
                if (inSession.user == null) {
                    provider.sendMessage(DefaultChannel.ERROR.name, "Your session is inactive.")
                } else {
                    provider.sendMessage(DefaultChannel.APPROVE_TOKEN.name, "success")
                }
            }
            DefaultChannel.GEN_TOKEN.name -> {
                val map = message.message as Map<String, *>
                if (map.containsKey("username") && map.containsKey("password") && map.containsKey("lifetime")) {
                    try {
                        provider.sendMessage(DefaultChannel.GEN_TOKEN.name, generateToken(map.get("username") as String, map.get("password") as String, (map.get("lifetime") as Double).toInt()))

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