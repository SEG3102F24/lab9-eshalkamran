package seg3x02.tempconverterapi

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.provisioning.InMemoryUserDetailsManager
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.web.SecurityFilterChain

@Configuration
class SecurityConfig {

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun userDetailsService(): InMemoryUserDetailsManager {
        val user1: UserDetails = User.builder()
            .username("user1")
            .password(passwordEncoder().encode("pass1"))
            .roles("USER")
            .build()
        val user2: UserDetails = User.builder()
            .username("user2")
            .password(passwordEncoder().encode("pass2"))
            .roles("USER")
            .build()
        return InMemoryUserDetailsManager(user1, user2)
    }

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf { csrf -> csrf.disable() }
            .authorizeHttpRequests { requests ->
                requests.anyRequest().authenticated()
            }
            .httpBasic { httpBasic -> httpBasic }
        return http.build()
    }
}
