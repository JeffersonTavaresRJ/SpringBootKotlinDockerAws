package br.com.kotlin.erudio.security.jwt

import br.com.kotlin.erudio.data.vo.v1.TokenVO
import br.com.kotlin.erudio.exception.model.InvalidJwtAuthenticationException
import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.interfaces.DecodedJWT
import jakarta.annotation.PostConstruct
import jakarta.servlet.http.HttpServletRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.util.*

@Service
class JwtTokenProvider {

    @Value("\${security.jwt.token.secret-key:secret}")
    private var secretKey: String = "secret";//valor sendo setado caso o arquivo application.yml esteja sem a informação..

    @Value("\${security.jwt.token.expired-length}")
    private var expiredLengthMilliseconds: Long=3_600_000;//valor sendo setado caso o arquivo application.yml esteja sem a informação..

    @Autowired
    private lateinit var userDetailsService: UserDetailsService;

    private lateinit var algorithm: Algorithm;

    @PostConstruct //chamado após tudo ser criado antes da ação do usuário..
    protected fun init(){
        secretKey = Base64.getEncoder().encodeToString(secretKey.toByteArray());
        algorithm = Algorithm.HMAC256(secretKey.toByteArray());
    }

    fun createAccessToken(username: String, roles: List<String>) : TokenVO{
        val now = Date();
        val validity = Date(now.time+expiredLengthMilliseconds)
        val accessToken = getAccessToken(username, roles, now, validity);
        val refreshToken = getRefreshToken(username, roles, now);
        return TokenVO(
            username = username,
            authenticated = true,
            accessToken = accessToken,
            refreshToken = refreshToken,
            created = now,
            expired = validity
        );
    }

    fun getAuthentication(token: String): Authentication{
        val decodedJWT: DecodedJWT = decodedToken(token);//descodificando o token...
        val userDetails: UserDetails = userDetailsService.loadUserByUsername(decodedJWT.subject);
        return UsernamePasswordAuthenticationToken(userDetails, "", userDetails.authorities);

    }

    fun resolveToken(req: HttpServletRequest):String?{
        val bearerToken = req.getHeader("Authorization");
        return if(!bearerToken.isNullOrBlank() && bearerToken.startsWith("Bearer")) {
            bearerToken.substring("Bearer ".length)
        }else null

    }

    fun validateToken(token: String): Boolean{
        try{
            val decodedJWT = decodedToken(token);
            if(decodedJWT.token.isNullOrBlank() && decodedJWT.expiresAt.before(Date())) return false;
            return true;
        }catch (e: Exception){
            throw  InvalidJwtAuthenticationException("Token inválido ou expirado!", "");
        }
    }

    private fun getAccessToken(username: String, roles: List<String>, now: Date, validity: Date): String {
        val isserURL: String = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
        return JWT.create().withClaim("roles",roles)
            .withIssuedAt(now)
            .withExpiresAt(validity)
            .withSubject(username)
            .withIssuer(isserURL)
            .sign(algorithm)
            .trim();
    }

    private fun getRefreshToken(username: String, roles: List<String>, now: Date): String {
        val validityRefreshToken = Date(now.time+expiredLengthMilliseconds*3);
        return JWT.create().withClaim("roles",roles)
            .withExpiresAt(validityRefreshToken)
            .withSubject(username)
            .sign(algorithm)
            .trim();
    }


    private fun decodedToken(token: String): DecodedJWT {
        val algorithm = Algorithm.HMAC256((secretKey.toByteArray()));
        val verifier: JWTVerifier = JWT.require(algorithm).build();
        return verifier.verify(token);
    }

    fun refreshToken(refreshToken: String) : TokenVO {
        var token: String = ""
        if(refreshToken.contains("Bearer ")) token = refreshToken.substring("Bearer ".length)
        val verifier: JWTVerifier = JWT.require(algorithm).build()

        //através do refreshToken, que pego o usuário e suas roles para criar um novo token..
        var decodedJWT: DecodedJWT = verifier.verify(token)

        val username: String = decodedJWT.subject
        val roles: List<String> = decodedJWT.getClaim("roles").asList(String::class.java)

        //criando um novo token..
        return createAccessToken(username, roles)
    }


}