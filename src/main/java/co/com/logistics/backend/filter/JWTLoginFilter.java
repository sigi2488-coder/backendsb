package co.com.logistics.backend.filter;

import co.com.logistics.backend.Repository.UsuarioRepository;
import co.com.logistics.backend.entity.Usuario;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;

public class JWTLoginFilter extends AbstractAuthenticationProcessingFilter {

    private final static Logger LOGGER = Logger.getLogger("JWTLoginFilter");
    
    //@Autowired
   // private UsuarioRepository usuarios;

    public JWTLoginFilter(String url, AuthenticationManager authManager) {
        super(new AntPathRequestMatcher(url));
        setAuthenticationManager(authManager);
    }

    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest req, HttpServletResponse res) {
        //throws AuthenticationException, IOException, ServletException,MismatchedInputException 
        AccountCredentials creds = null;
        try {
            creds = new ObjectMapper()
                    .readValue(req.getInputStream(), AccountCredentials.class);
        } catch (JsonParseException e) {
            LOGGER.log(Level.INFO, e.getMessage());//e.printStackTrace();
            creds = null;
        } catch (JsonMappingException e) {
            LOGGER.log(Level.INFO, e.getMessage());//e.printStackTrace();
            creds = null;
        } catch (IOException e) {
            LOGGER.log(Level.INFO, e.getMessage());
            creds = null;
        } catch (Exception e) {
            LOGGER.log(Level.INFO, e.getMessage());
            creds = null;
        }

        if (null != creds) {
            return getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(
                            creds.getUsername(),
                            creds.getPassword(),
                            Collections.emptyList()
                    )
            );
        } else {
            return null;
        }
    }

    @Override
    protected void successfulAuthentication(
            HttpServletRequest req,
            HttpServletResponse res, FilterChain chain,
            Authentication auth) throws IOException, ServletException {
        /*UsuarioRepository usuarios = new UsuarioRepository();
        Usuario usr = usuarios.getUsuarioAuth(auth.getName());*/
        
        TokenAuthenticationService
                .addAuthentication(res, auth.getName());
    }
}
