package project.spring.jdbc.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity(
        securedEnabled = true,
        jsr250Enabled = true
)
public class SecurityConfigurer {
    public static final String[] WHITE_LIST = {"/auth/login", "/css/**", "/auth/register"};
    private final CustomUserDetailsService userDetailsService;
    private final CustomFailureHandler customFailureHandler;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);
        http.userDetailsService(userDetailsService);

        http.authorizeHttpRequests(authorizeRequests ->
                authorizeRequests.requestMatchers(WHITE_LIST).permitAll()
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .anyRequest()
                        .authenticated());

        http.formLogin(form ->
            form.loginPage("/auth/login")
                .usernameParameter("uname")
                .passwordParameter("pwd")
                .defaultSuccessUrl("/", false)
                .failureUrl("/auth/login?error=true")
                .failureHandler(customFailureHandler)
        );
        http.logout(logout -> {
            logout.logoutUrl("/auth/logout")
                    .deleteCookies("JSESSIONID")
                    .clearAuthentication(true)
                    .logoutRequestMatcher(new AntPathRequestMatcher("/auth/logout", "POST"));
        });
        http.rememberMe(rememberMeConfigurer -> {
            rememberMeConfigurer.rememberMeParameter("remember")
                    .rememberMeCookieName("rem-me")
                    .tokenValiditySeconds(24*60*60)
                    .key("Xushnudbek20040909$")
                    .alwaysRemember(true);
        });
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
