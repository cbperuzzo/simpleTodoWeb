package com.lux.simpleTodoWeb.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.configurers.provisioning.UserDetailsManagerConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsPasswordService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;

import java.util.function.Function;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SpringSecurityConfiguration {

    @Bean
    public InMemoryUserDetailsManager configureUserDetailsManager() {

        UserDetails ud1 = getUserDetails("eu","123");
        UserDetails ud2 = getUserDetails("tu","321");

        return new InMemoryUserDetailsManager(ud1,ud2);
    }


    private UserDetails getUserDetails(String name,String pass) {

        Function<String,String> passwordEncoder =
                x -> passwordEncoder().encode(x); ;

        return User.builder()
                .passwordEncoder(passwordEncoder)
                .username(name)
                .password(pass)
                .roles("USER","ADMIN")
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain FilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> auth.anyRequest().authenticated());

        http.formLogin(withDefaults());

        http.csrf((csrf) -> csrf
                .csrfTokenRepository(new HttpSessionCsrfTokenRepository())
        );

        return http.build();
    }
}
