package com.projekat.projekat_mrs_isa.config;


import com.projekat.projekat_mrs_isa.security.auth.RestAuthenticationEntryPoint;
import com.projekat.projekat_mrs_isa.security.auth.TokenAuthenticationFilter;
import com.projekat.projekat_mrs_isa.service.UserService;
import com.projekat.projekat_mrs_isa.util.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;


@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private PasswordEncoderComponent passwordEncoderComponent;

    @Autowired
    private UserService userService;

    @Autowired
    private RestAuthenticationEntryPoint restAuthenticationEntryPoint;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
        auth
                .userDetailsService(userService)
                .passwordEncoder(passwordEncoderComponent.getPasswordEncoder());
    }

    @Autowired
    private TokenUtils tokenUtils;

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .exceptionHandling().authenticationEntryPoint(restAuthenticationEntryPoint).and()
                .authorizeRequests()
                                    //.antMatchers("/api/clients/isMailAvailable/**").permitAll()
                                   // .antMatchers("/api/isUsernameAvailable/**").permitAll()
                                    .antMatchers("/api/auth/**").permitAll()
                                    .antMatchers("/api/fishingClasses/**").permitAll()
                                    .antMatchers("/api/fishingInstructors/**").permitAll()
                                    //.antMatchers("/api/offers/**").permitAll()
                                    .antMatchers("/api/reservations/**").permitAll()
                                    .antMatchers("/api/ships/**").permitAll()
                                    .antMatchers("/api/shipOwners/**").permitAll()
//                                    .antMatchers("/api/vacation_houses/all").permitAll()
//                                    .antMatchers("/api/vacationHouseOwners/**").permitAll()
//                                    .antMatchers("/api/vacation_houses/all/loggedVacationHouseOwner").permitAll()
                .anyRequest().authenticated().and()
                .cors().and()
                .addFilterBefore(new TokenAuthenticationFilter(tokenUtils,userService), BasicAuthenticationFilter.class);
        http.csrf().disable();
    }

    @Override
    public void configure(WebSecurity web)throws Exception{
        web.ignoring().antMatchers(HttpMethod.POST,"/api/auth/login");
        web.ignoring().antMatchers(HttpMethod.GET,"/api/auth/isMailAvailable/**");
        web.ignoring().antMatchers(HttpMethod.GET,"/api/auth/isUsernameAvailable/**");
        web.ignoring().antMatchers(HttpMethod.GET,"/api/auth/verify/*");

        web.ignoring().antMatchers(HttpMethod.GET,"/api/vacationHouses/anyUser/**");

        web.ignoring().antMatchers(HttpMethod.POST,"/api/auth/addClient");
        web.ignoring().antMatchers(HttpMethod.GET, "/", "/webjars/**", "/*.html", "favicon.ico", "/**/*.html",
                "/**/*.css", "/**/*.js");
    }

}
