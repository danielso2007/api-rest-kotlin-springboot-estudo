package br.com.forum.config

import br.com.forum.config.filter.JwtAuthenticationFilter
import br.com.forum.config.filter.JwtLoginFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.http.HttpMethod
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configurers.*
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.web.util.matcher.AntPathRequestMatcher


@Configuration
@EnableWebSecurity
class SecurityConfiguration(
    private val userDetailsService: UserDetailsService,
    private val authenticationConfiguration: AuthenticationConfiguration,
    private val jwtUtil: JwtUtil
) {

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
                            AntPathRequestMatcher("/v1/topicos"),
                            AntPathRequestMatcher("/v1/usuarios"),
                            AntPathRequestMatcher("/v1/topicos/relatorio"),
                            AntPathRequestMatcher("/v1/cursos"))
                        .hasAnyAuthority("LEITURA_ESCRITA")
                        .requestMatchers(AntPathRequestMatcher("/v1/auth", HttpMethod.POST.name()))
                        .permitAll()
                        .anyRequest()
                        .authenticated()
                }
            )
            .addFilterBefore(
                JwtLoginFilter(authManager = authenticationConfiguration.authenticationManager, jwtUtil = jwtUtil),
                UsernamePasswordAuthenticationFilter().javaClass
            )
            .addFilterBefore(
                JwtAuthenticationFilter(jwtUtil = jwtUtil),
                UsernamePasswordAuthenticationFilter::class.java
            )
            .sessionManagement(Customizer { sessionManagementConfigurer: SessionManagementConfigurer<HttpSecurity> ->
                sessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            })
            .formLogin(Customizer { formLoginConfigurer: FormLoginConfigurer<HttpSecurity> ->
                formLoginConfigurer.disable()
                formLoginConfigurer.failureForwardUrl("/api/error")
                formLoginConfigurer.loginPage("/v1/auth")
            })
            .httpBasic(Customizer { httpBasicConfigurer: HttpBasicConfigurer<HttpSecurity> ->
            httpBasicConfigurer.disable()
            })
        return http.build()
    }

    @Bean
    fun encoder(): PasswordEncoder? {
        return BCryptPasswordEncoder()
    }

    @Bean
    @Primary
    fun configure(auth: AuthenticationManagerBuilder): AuthenticationManagerBuilder {
        auth.userDetailsService(userDetailsService).passwordEncoder(encoder())
        return auth
    }

//    @Bean
//    fun webSecurityCustomizer(): WebSecurityCustomizer? {
//        return WebSecurityCustomizer { web: WebSecurity ->
//            web.ignoring()
//                .requestMatchers(
//                    "/h2-console/*",
//                    "/configuration/**",
//                    "/swagger-resources/**"
//                )
//        }
//    }

}