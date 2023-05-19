package ke.co.rafiki.fmis.security;

import ke.co.rafiki.fmis.domain.enums.RoleType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    private final UserDetailsService userDetailsService;

    public SecurityConfiguration(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/").permitAll();
                    auth.requestMatchers("/actuator/**").permitAll();
                    auth.requestMatchers("/auth/register").permitAll();
                    auth.requestMatchers("/auth/logout").permitAll();
                    auth.requestMatchers("/users").hasAuthority(RoleType.ADMIN.toString());
                    auth.anyRequest().authenticated();
                })
                .userDetailsService(userDetailsService)
                .logout(configurer -> configurer.logoutUrl("/auth/logout"))
                .csrf(AbstractHttpConfigurer::disable) // disable CSRF
                .cors(Customizer.withDefaults())
                .httpBasic(Customizer.withDefaults()) // use Basic authentication
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() { return  new BCryptPasswordEncoder(); }
}
