package BankingSystem;

import java.beans.BeanProperty;
    //import java.beans.Customizer;
    import org.springframework.context.annotation.Bean;
    import org.springframework.context.annotation.Configuration;
    import org.springframework.security.config.annotation.web.builders.HttpSecurity;
    import org.springframework.security.web.SecurityFilterChain;
    import org.springframework.security.config.Customizer;
    import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
    import org.springframework.security.crypto.password.PasswordEncoder;
    @Configuration
    public class SecurityConfig{

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
            http.csrf(csrf -> csrf.disable()).authorizeHttpRequests(auth -> auth.requestMatchers("/customers","/users").permitAll()
                    .anyRequest().authenticated()).httpBasic(Customizer.withDefaults());
            return http.build();

        }

        @Bean
        public PasswordEncoder password(){
            return new BCryptPasswordEncoder();
        }
    }
