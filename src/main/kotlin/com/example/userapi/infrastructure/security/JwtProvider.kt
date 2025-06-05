package com.example.userapi.infrastructure.security

import com.example.userapi.domain.model.Role
import com.example.userapi.domain.model.User
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.*
import javax.crypto.spec.SecretKeySpec

@Component
class JwtProvider(
    @Value("\${jwt.secret}") private val secret: String,
    @Value("\${jwt.access-token-expiration}") private val accessTokenExpiration: Long,
    @Value("\${jwt.refresh-token-expiration}") private val refreshTokenExpiration: Long,
) {

    // HS512 알고리즘용 키 생성
    private val key = SecretKeySpec(secret.toByteArray(), SignatureAlgorithm.HS512.jcaName)

    fun createAccessToken(user: User): String {
        return generateToken(user.email, user.role, accessTokenExpiration)
    }

    fun createRefreshToken(user: User): String {
        return generateToken(user.email, user.role, refreshTokenExpiration)
    }

    private fun generateToken(email: String, role: Role, expiration: Long): String {
        val claims = Jwts.claims().setSubject(email)
        claims["role"] = role.name

        return Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(Date())
            .setExpiration(Date(System.currentTimeMillis() + expiration))
            .signWith(key, SignatureAlgorithm.HS512)   // HS512로 변경
            .compact()
    }

    fun getEmail(token: String): String =
        parseClaims(token).subject

    fun getRole(token: String): String =
        parseClaims(token)["role"].toString()

    fun validateToken(token: String): Boolean =
        try {
            !parseClaims(token).expiration.before(Date())
        } catch (e: Exception) {
            false
        }

    private fun parseClaims(token: String): Claims =
        Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)
            .body
}
