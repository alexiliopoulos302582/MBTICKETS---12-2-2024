package com.ticketapplication.security;


import com.ticketapplication.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
@EnableWebSecurity
public class ticketSecurity {


    @Autowired
    passwordEncoder passwordEncoder;

    @Autowired
    private UserServiceImpl userService;


//    This part configures the authentication manager to use
//    the userDetailsService provided by the injected userService.
//    The userDetailsService method is typically responsible for
//    loading user-specific data during the authentication process.
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService)
                .passwordEncoder(passwordEncoder.bCryptPasswordEncoder());
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity.csrf(csrf -> csrf.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()));

        httpSecurity.authorizeHttpRequests(
                auth->auth.requestMatchers("/admin").hasAnyAuthority("ADMIN","USER")
                        .requestMatchers("/landing","adduser","/company.jpg").permitAll()
                        .anyRequest().authenticated())
                .formLogin(form->
                        form.permitAll().defaultSuccessUrl("/home",true))
                .logout(logout->logout.logoutUrl("/logout")
                        .logoutSuccessUrl("/landing").invalidateHttpSession(true).deleteCookies("JSESSIONID"))
                .exceptionHandling(expeptionHandling ->expeptionHandling.accessDeniedPage("/access-denied"));
        httpSecurity.httpBasic(Customizer.withDefaults());
        return httpSecurity.build();


    }

}
