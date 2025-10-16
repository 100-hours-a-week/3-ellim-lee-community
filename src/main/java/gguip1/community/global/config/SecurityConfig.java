package gguip1.community.global.config;

import gguip1.community.global.exception.ErrorCode;
import gguip1.community.global.exception.ErrorException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.servlet.HandlerExceptionResolver;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final HandlerExceptionResolver handlerExceptionResolver;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
//            .csrf(csrf -> csrf
//                    .ignoringRequestMatchers("/auth", "/users")
//                     .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
//                    /*t
//                     * Spring Security CSRF 설정
//                     * - /auth, /users 경로는 CSRF 보호에서 제외
//                     * - 로그인과 회원가입 요청은 CSRF 토큰 없이 허용
//                     */
//            )
            .csrf(AbstractHttpConfigurer::disable)
            /*
             * CSRF 보호 비활성화
             * - RESTful API 서버에서는 일반적으로 CSRF 보호가 필요하지 않음
             * - 클라이언트가 CSRF 토큰을 관리하지 않아도 됨
             */
            .authorizeHttpRequests(auth -> auth
                    .requestMatchers("/auth", "/users").permitAll()
                    .anyRequest().authenticated()
                    /*
                     * Authorization 설정
                     * - /auth, /users 경로는 인증 없이 접근 허용
                     * - 그 외의 모든 요청은 인증 필요
                     */
            )
            .httpBasic(basic -> basic.disable()
                    /*
                     * HTTP Basic 인증 비활성화
                     * - 기본 제공되는 HTTP Basic 인증을 사용하지 않음
                     * - 클라이언트가 userId, password를 매 요청마다 보내지 않도록 설정
                     * - 세션 기반 인증을 사용하여 로그인 상태 유지
                     */
            )
            .sessionManagement(session -> session
                    .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                    .maximumSessions(3)
                    .maxSessionsPreventsLogin(false) // true면 중복 로그인 차단, false면 기존 세션 만료
                    .expiredSessionStrategy(event -> handlerExceptionResolver.resolveException(
                            event.getRequest(),
                            event.getResponse(),
                            null,
                            new ErrorException(ErrorCode.SESSION_EXPIRED)
                    ))
                    /*
                     * 세션 관리 설정
                     * - 세션 생성 정책을 IF_REQUIRED로 설정하여 필요 시에만 세션 생성
                     * - 최대 3개의 세션 허용, 초과 시 기존 세션 만료
                     * - 지정한 최대 세션 초과 시 새로운 로그인 허용 (기존 세션 만료)
                     * - 세션 만료 시 커스텀 핸들러로 예외 처리
                     */
            )
            .exceptionHandling(ex -> ex
                    .authenticationEntryPoint((request, response, authException) -> {
                        handlerExceptionResolver.resolveException(
                                request,
                                response,
                                null,
                                new ErrorException(ErrorCode.INVALID_CREDENTIALS)
                        );
                    })
                    .accessDeniedHandler(((request, response, accessDeniedException) -> {
                        handlerExceptionResolver.resolveException(
                                request,
                                response,
                                null,
                                new ErrorException(ErrorCode.ACCESS_DENIED)
                        );
                    }))
                    /*
                     * 예외 처리 설정
                     * - 인증 실패 시 커스텀 핸들러로 예외 처리
                     * - 권한 부족 시 커스텀 핸들러로 예외 처리
                     */
            );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        /*
         * 비밀번호 암호화 설정
         * - BCryptPasswordEncoder 사용
         * - 비밀번호를 안전하게 저장하기 위해 해시 함수 적용
         */
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration config) throws Exception {
        /*
         * AuthenticationManager 빈 등록
         */
        return config.getAuthenticationManager();
    }
}
