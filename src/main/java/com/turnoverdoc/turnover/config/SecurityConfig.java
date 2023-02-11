package com.turnoverdoc.turnover.config;

import com.turnoverdoc.turnover.security.jwt.JwtConfigurer;
import com.turnoverdoc.turnover.security.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final JwtTokenProvider jwtTokenProvider;

    private static final String ADMIN_BASEURL = "/api/v1/admin/**";
    private static final String LOGIN_BASEURL = "/api/v1/auth/login/**";
    private static final String ADMIN_LOGIN_BASEURL = "/api/v1/auth/admin/login";
    private static final String REGISTRATION_BASEURL = "/api/v1/auth/registration";
    private static final String AUTH_ADMIN_BASEURL = "/api/v1/super-admin/auth/**";
    private static final String RESET_PASSWORD_BASEURL = "/api/v1/resetPassword/**";

    @Autowired
    public SecurityConfig(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.
                httpBasic().disable().
                csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(LOGIN_BASEURL).permitAll()
                .antMatchers(REGISTRATION_BASEURL).permitAll()
                .antMatchers(RESET_PASSWORD_BASEURL).permitAll()
                .antMatchers(ADMIN_BASEURL).hasAnyRole("ADMIN","SUPER_ADMIN")
                .antMatchers(ADMIN_LOGIN_BASEURL).permitAll()
                .antMatchers(AUTH_ADMIN_BASEURL).hasRole("SUPER_ADMIN")
                .anyRequest().authenticated()
                .and()
                .apply(new JwtConfigurer(jwtTokenProvider))
                .and()
                .logout()
                .logoutUrl(LOGIN_BASEURL +"/logout")
                .invalidateHttpSession(true)
                .deleteCookies("token");
    }
    //Config swagger
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/v2/api-docs",
                "/configuration/ui",
                "/swagger-resources/**",
                "/configuration/security",
                "/swagger-ui.html",
                "/webjars/**");
    }
}
