package gguip1.community.domain.auth.controller;

import gguip1.community.domain.auth.dto.AuthRequest;
import gguip1.community.domain.auth.dto.AuthResponse;
import gguip1.community.domain.auth.service.AuthService;
import gguip1.community.global.response.ApiResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/auth")
    public ResponseEntity<ApiResponse<AuthResponse>> login(@RequestBody AuthRequest authRequest,
                                                           HttpServletRequest httpRequest) {
        AuthResponse authResponse = authService.login(authRequest, httpRequest);

        /*
         * 테스트해보고 이상 없으면 지워주세요.
         */
//        HttpSession session = httpRequest.getSession(true);
//        session.setAttribute(
//                HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
//                SecurityContextHolder.getContext()
//        );

        return ResponseEntity.status(HttpStatus.OK).body(
                ApiResponse.success("login_success", authResponse)
        );
    }

    @DeleteMapping("/auth")
    public ResponseEntity<ApiResponse<Void>> logout(HttpServletRequest httpRequest,
                                                    HttpServletResponse httpResponse) {
        SecurityContextHolder.clearContext();

        HttpSession session = httpRequest.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        Cookie cookie = new Cookie("JSESSIONID", null);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        httpResponse.addCookie(cookie);

        return ResponseEntity.noContent().build();
    }
}
