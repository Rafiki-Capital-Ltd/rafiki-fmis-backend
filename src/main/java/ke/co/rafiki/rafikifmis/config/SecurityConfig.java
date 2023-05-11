package ke.co.rafiki.rafikifmis.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/").permitAll();
                    auth.requestMatchers("/actuator/**").permitAll();
                    auth.requestMatchers("/auth/register").permitAll();
                    auth.requestMatchers("/auth/logout").permitAll();
                    auth.anyRequest().authenticated();
                })
                .logout(configurer -> configurer.logoutUrl("/auth/logout"))
                .csrf(AbstractHttpConfigurer::disable) // disable CSRF
                .httpBasic(Customizer.withDefaults()) // use Basic authentication
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() { return  new BCryptPasswordEncoder(); }
}
