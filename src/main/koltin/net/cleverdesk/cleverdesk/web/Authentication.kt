package net.cleverdesk.cleverdesk.web

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.SignatureException
import io.jsonwebtoken.impl.crypto.MacProvider
import net.cleverdesk.cleverdesk.User
import net.cleverdesk.cleverdesk.launcher.Launcher

/**
 * Created by schulerlabor on 21.06.16.
 */
class Authentication(launcher: Launcher) {

    private final val apiKey = MacProvider.generateKey()
    private final val launcher = launcher


    fun generateToken(username: String, password: String): String {
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

        return Jwts.builder().setSubject(user.uuid).signWith(alg, apiKey).compact()

    }

    fun authUser(token: String): User {
        try {
            val uuid = Jwts.parser().setSigningKey(apiKey).parseClaimsJws(token).body.subject
            val user: User = User(launcher)
            user.uuid = uuid
            if (launcher.database == null) {
                throw AuthenticationException("Internal error")
            }
            launcher.database!!.download(user, user)
            if (user.username == null) {
                throw AuthenticationException("Invalid token")

            }
            return user

        } catch(e: SignatureException) {
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
}