package com.example.userapi.common.security

import com.example.userapi.common.exception.CommonExceptionConst
import com.example.userapi.common.exception.InvalidJwtFormatException
import com.example.userapi.common.exception.JwtGenerateTokenException
import com.example.userapi.domain.model.Role
import com.example.userapi.domain.model.UserAdapterDto
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.*
import javax.crypto.spec.SecretKeySpec
import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 * JWT 생성 및 검증
 * - JWT를 생성하고 검증하는 역할을 합니다.기본적인 HS256 알고리즘을 사용하여 JWT를 생성하고, 클레임을 설정한 후 서명합니다.
 */
@Component
class JwtProvider(
    @Value("\${jwt.secret}") private val secret: String,
    @Value("\${jwt.access-token-expiration}") private val accessTokenExpiration: Long,
    @Value("\${jwt.refresh-token-expiration}") private val refreshTokenExpiration: Long,
) {

    private val key = SecretKeySpec(secret.toByteArray(), SignatureAlgorithm.HS256.jcaName)

    fun createAccessToken(userAdapterDto: UserAdapterDto): String {
        return generateToken(userAdapterDto.email, userAdapterDto.role, accessTokenExpiration)
    }

    fun createRefreshToken(userAdapterDto: UserAdapterDto): String {
        return generateToken(userAdapterDto.email, userAdapterDto.role, refreshTokenExpiration)
    }

    private fun generateToken(email: String, role: Role, expiration: Long): String {
        val claims = Jwts.claims().setSubject(email)
        claims["role"] = role.name

        return try {
            Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(Date())
                .setExpiration(Date(System.currentTimeMillis() + expiration))
                .signWith(key)
                .compact()
        } catch (e: Exception) {
            throw JwtGenerateTokenException(CommonExceptionConst.TOKEN_GENERATE_ERROR)
        }
    }

    fun getEmail(token: String): String =
        parseClaims(token).subject

    fun getRole(token: String): String =
        parseClaims(token)["role"].toString()

    fun validateToken(token: String): Boolean =
        try {
            !parseClaims(token).expiration.before(Date())
        } catch (e: Exception) {
            // todo Exception 세분화 필요
            throw InvalidJwtFormatException(CommonExceptionConst.TOKEN_FORMAT_ERROR)
        }

    private fun parseClaims(token: String): Claims =
        Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)
            .body
}
