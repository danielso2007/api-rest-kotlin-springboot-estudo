package br.com.forum.config

import br.com.forum.service.UsuarioService
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.UnsupportedJwtException
import io.jsonwebtoken.security.SignatureException
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.stereotype.Component
import java.security.Key
import java.util.*
import javax.crypto.spec.SecretKeySpec


@Component
class JwtUtil(
    private val expiration: Long = 240000,
    private val usuarioService: UsuarioService
) {

    @Value("\${jwt.secret}")
    private lateinit var jwtKey: String

    fun generateToken(username: String, authorities: MutableCollection<out GrantedAuthority>): String {
        return Jwts.builder()
            .setSubject(username)
            .claim("role", authorities)
            .setHeaderParam("typ", "JWT")
            .claim("data", "flow")
            .setIssuedAt(Date())
            .setExpiration(Date(System.currentTimeMillis() + expiration))
            .signWith(getKey(jwtKey))
            .compact()
    }

    fun isValid(jwt: String?): Boolean {
        try {
            Jwts.parserBuilder().requireAudience(jwtKey).build().parse(jwt)
            //Jwts.parser().setSigningKey(getKey(jwtKey)).parseClaimsJws(jwt)
            return true
        } catch (e: SignatureException) {
            println("Invalid JWT signature: " + e.message)
        } catch (e: MalformedJwtException) {
            println("Invalid JWT token: " + e.message)
        } catch (e: ExpiredJwtException) {
            println("JWT token is expired: " + e.message)
        } catch (e: UnsupportedJwtException) {
            println("JWT token is unsupported: " + e.message)
        } catch (e: IllegalArgumentException) {
            println("JWT claims string is empty: " + e.message)
        }
        return false
    }

    fun getAuthentication(jwt: String?): Authentication {
        val username = Jwts.parserBuilder().requireAudience(jwtKey).build().parseClaimsJws(jwt).body.subject
        //val username = Jwts.parser().setSigningKey(getKey(jwtKey)).parseClaimsJws(jwt).body.subject
        val user = usuarioService.loadUserByUsername(username)
        return UsernamePasswordAuthenticationToken(username, null, user.authorities)
    }

    fun getKey(jwtKey: String): Key = SecretKeySpec(jwtKey.toByteArray(), SignatureAlgorithm.HS256.jcaName)
}