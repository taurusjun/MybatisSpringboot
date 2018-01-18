package tk.mybatis.springboot.conf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import tk.mybatis.springboot.auth.JWTLoginFilter;
import tk.mybatis.springboot.auth.JwtAuthenticationFilter;

public class MyWebSecurityConfig
        extends WebSecurityConfigurerAdapter
{
    @Autowired
    private UserDetailsService userAuth;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder()
    {
        return new BCryptPasswordEncoder();
    }

    protected void configure(HttpSecurity http)
            throws Exception
    {
        ((HttpSecurity)((ExpressionUrlAuthorizationConfigurer.AuthorizedUrl)((ExpressionUrlAuthorizationConfigurer.AuthorizedUrl)((HttpSecurity)((HttpSecurity)http.cors().and()).csrf().disable()).authorizeRequests().antMatchers(HttpMethod.POST, new String[] { "/users/add" })).permitAll().anyRequest()).authenticated().and()).addFilter(new JWTLoginFilter(authenticationManager())).addFilter(new JwtAuthenticationFilter(authenticationManager()));
    }

    public void configure(AuthenticationManagerBuilder auth)
            throws Exception
    {
        auth.userDetailsService(this.userAuth);
    }
}
