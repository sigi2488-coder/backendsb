package co.com.logistics.backend.filter;

import co.com.logistics.backend.Repository.UsuarioRepository;
import co.com.logistics.backend.entity.Usuario;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.util.Collections.emptyList;


public class TokenAuthenticationService {

    static final long EXPIRATIONTIME = 864_000_000; // 10 days
    static final String SECRET = "S3cR3t0//Enerttl";
    static final String TOKEN_PREFIX = "Bearer";
    static final String HEADER_STRING = "Authorization";
    private final static Logger LOGGER = Logger.getLogger("TokenAuthenticationService");
    

    static void addAuthentication(HttpServletResponse res, String username) {

        Date md = new Date(System.currentTimeMillis() + EXPIRATIONTIME);

        String JWT = Jwts.builder()
                .setSubject(username)
                .setExpiration(md)
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
        ResultJWT rjwt = new ResultJWT();
        rjwt.setToken(TOKEN_PREFIX + " " + JWT);
        ObjectMapper objectMapper = new ObjectMapper();
        rjwt.setExpira(String.valueOf(md.getTime()));
        String str_header = "";
        try {
            str_header = objectMapper.writeValueAsString(rjwt);
        } catch (JsonProcessingException e) {
            str_header = e.getMessage();
            LOGGER.log(Level.INFO, e.getMessage());
        }
        res.addHeader("Access-Control-Expose-Headers", "Authorization");
        res.addHeader(HEADER_STRING, str_header);
    }

    static Authentication getAuthentication(HttpServletRequest request) {

        String token = request.getHeader(HEADER_STRING);
        if (token != null) {
            // parse the token.
            String user = null;
            try {
                if (token.contains(TOKEN_PREFIX)) {
                    user = Jwts.parser()
                            .setSigningKey(SECRET)
                            .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                            .getBody()
                            .getSubject();
                } else {
                    user = null;
                }
            } catch (MalformedJwtException e) {
                user = null;
            } catch (SignatureException e) {
                user = null;
            }

            return user != null
                    ? new UsernamePasswordAuthenticationToken(user, null, emptyList())
                    : null;
        }
        return null;
    }

}
