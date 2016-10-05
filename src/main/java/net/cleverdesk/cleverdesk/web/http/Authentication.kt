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
package net.cleverdesk.cleverdesk.web.http

import io.jsonwebtoken.JwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.impl.crypto.MacProvider
import net.cleverdesk.cleverdesk.User
import net.cleverdesk.cleverdesk.launcher.Launcher
import org.apache.commons.lang3.time.DateUtils
import java.util.*

/**
 * Created by schulerlabor on 21.06.16.
 */
class Authentication(launcher: Launcher) {

    private final val apiKey = MacProvider.generateKey()
    private final val launcher = launcher


    fun generateToken(username: String, password: String, liftime_seconds: Int): String {
        val user: User = User(launcher)
        user.username = username
        if (launcher.database == null) {
            throw AuthenticationException("Internal error")
        }

        launcher.database!!.download(user, user)

        if (user.password == null || user.password != hashPassword(password)) {
            throw AuthenticationException("Incorrect credentials")
        }

        val alg: SignatureAlgorithm = SignatureAlgorithm.HS256
        val exp: Date = DateUtils.addSeconds(Date(), liftime_seconds)

        return Jwts.builder()
                .setId(user.uuid)
                .signWith(alg, apiKey)
                .setNotBefore(Date())
                .setExpiration(exp)
                .setSubject(user.password)
                .compact()


    }

    fun authUser(token: String): User {
        try {
            val claims = Jwts.parser().setSigningKey(apiKey).parseClaimsJws(token).body

            val uuid = claims.id
            val user: User = User(launcher)
            user.uuid = uuid
            if (launcher.database == null) {
                throw AuthenticationException("Internal error")
            }
            launcher.database!!.download(user, user)
            if (user.password != claims.subject) {
                throw AuthenticationException("Invalid token")

            }
            return user

        } catch(e: JwtException) {
            throw AuthenticationException("Invalid token")
        }
    }

    private fun hashPassword(password: String): String {
        return password
    }
}

class AuthenticationException(override val message: String?) : Exception() {

}

class AuthRequest() {
    public var username: String? = null
    public var password: String? = null
    public var lifetime: Int? = null

}