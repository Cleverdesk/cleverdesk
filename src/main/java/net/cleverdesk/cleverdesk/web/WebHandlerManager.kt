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
package net.cleverdesk.cleverdesk.web

/**
 * Handels a web request.
 */
class WebHandlerManager : WebHandler {
    /**
     * Register for all channels registered [handlerChannels].
     */
    override val forChannels: Array<String>
        get() = handlerChannels.keys.toTypedArray()

    /**
     * Redirect message to all handler which use [channel] as Channel.
     */
    override fun handleMessage(provider: WebResponseProvider, inSession: WebSession<*>, message: WebMessage) {
        if (!handlerChannels.containsKey(message.channel)) return;
        for (handler in handlerChannels.get(message.channel)!!) {
            handler.handleMessage(provider, inSession, message)
        }
    }

    /**
     * Stores all registered handlers.
     */
    private val handlers: MutableList<WebHandler> = mutableListOf()
    /**
     * Stores all registered handlers in dependency of their channel.
     */
    private val handlerChannels: MutableMap<String, MutableList<WebHandler>> = mutableMapOf()

    /**
     * Registers a [handler] which will be called if a channel he registered will be used.
     */
    fun registerHandler(handler: WebHandler) {
        for (channel in handler.forChannels) {
            if (handlerChannels.containsKey(channel)) {
                handlerChannels.get(channel)!!.add(handler);
                handlerChannels.get(channel)!!.remove(handler);
            }
            handlerChannels.put(channel, mutableListOf(handler))
        }
        handlers.add(handler)
    }

    /**
     * Removes the [handler] from all registers and removes the channel if it is not used by other handlers.
     */
    fun unregisterHandler(handler: WebHandler) {
        for (channel in handler.forChannels) {
            //Remove handler from register.
            handlerChannels.get(channel)?.remove(handler)
            //Remove channel if unused.
            if (handlerChannels.containsKey(channel) && handlerChannels.get(channel)?.size!! < 1) {
                handlerChannels.remove(channel)
            }
        }
        //Remove handler from general register.
        handlers.remove(handler)
    }


}