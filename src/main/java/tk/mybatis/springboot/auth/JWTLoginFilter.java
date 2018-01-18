package tk.mybatis.springboot.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import tk.mybatis.springboot.model.UserInfo;

public class JWTLoginFilter
        extends UsernamePasswordAuthenticationFilter
{
    private AuthenticationManager authenticationManager;

    public JWTLoginFilter(AuthenticationManager authenticationManager)
    {
        this.authenticationManager = authenticationManager;
    }

    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res)
            throws AuthenticationException
    {
        try
        {
            UserInfo user = (UserInfo)new ObjectMapper().readValue(req.getInputStream(), UserInfo.class);

            return this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user

                    .getUsername(), user
                    .getPassword(), new ArrayList()));
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain, Authentication auth)
            throws IOException, ServletException
    {
        String token = Jwts.builder().setSubject(((User)auth.getPrincipal()).getUsername()).setExpiration(new Date(System.currentTimeMillis() + 86400000L)).signWith(SignatureAlgorithm.HS512, "MyJwtSecret").compact();
        res.addHeader("Authorization", "Bearer " + token);
    }
}
