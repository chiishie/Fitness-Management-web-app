package com.app.fitnessmanagement.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserDetailsService userDetailsService;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(Customizer.withDefaults())
                .authorizeHttpRequests(
                        authorizeRequest -> authorizeRequest
                                .requestMatchers("/", "/login", "/register", "/static/**", "/resources/**", "/static/css/**", "/static/images/**").permitAll()
                                .requestMatchers("/users/dashboard").hasAnyAuthority("ADMIN", "CUSTOMER")
                                .requestMatchers("/users/**").hasAuthority("ADMIN")
                                .anyRequest().permitAll()
                ).formLogin(formLogin -> formLogin
                                .loginPage("/login")
                                .usernameParameter("email") // Update to use email parameter
                                .defaultSuccessUrl("/", true)
                                .failureHandler((request, response, exception) -> {
                                    String email = request.getParameter("email");
                                    response.sendRedirect("/login?error=true&email=" + email);
                                })
//                        .failureUrl("/login?error=true") // Redirect to login with an error parameter
                ).logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                );
        return httpSecurity.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

}