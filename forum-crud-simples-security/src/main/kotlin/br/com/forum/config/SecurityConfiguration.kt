package br.com.forum.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configurers.*
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.util.matcher.AntPathRequestMatcher


@Configuration
@EnableWebSecurity
class SecurityConfiguration(private val userDetailsService : UserDetailsService) {

    @Bean
    @Throws(Exception::class)
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain? {
        http
            .csrf(Customizer { csrfConfigurer: CsrfConfigurer<HttpSecurity> ->
                csrfConfigurer.disable()
            })
            .authorizeHttpRequests(
                Customizer { authorizeHttpRequests: AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry ->
                    authorizeHttpRequests
                        .requestMatchers(
                            AntPathRequestMatcher("/topicos"),
                            AntPathRequestMatcher("/usuarios"),
                            AntPathRequestMatcher("/cursos"))
                        .hasAnyAuthority("LEITURA_ESCRITA")
                        .anyRequest().authenticated()
                }
            )
            .sessionManagement(Customizer { sessionManagementConfigurer : SessionManagementConfigurer<HttpSecurity> ->
                sessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            })
            .formLogin(Customizer { formLoginConfigurer : FormLoginConfigurer<HttpSecurity> ->
                formLoginConfigurer.disable()
            })
            .httpBasic(Customizer { httpBasicConfigurer : HttpBasicConfigurer<HttpSecurity> ->
            })
        return http.build()
    }

    @Bean
    fun bCryptPasswordEncoder(): BCryptPasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    @Primary
    fun configure(auth: AuthenticationManagerBuilder): AuthenticationManagerBuilder {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder())
        return auth
    }

}