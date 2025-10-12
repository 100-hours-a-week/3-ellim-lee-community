package gguip1.community.domain.auth.controller;

import gguip1.community.domain.auth.dto.AuthRequest;
import gguip1.community.domain.auth.service.AuthService;
import gguip1.community.global.annotation.RequireAuth;
import gguip1.community.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * 인증 컨트롤러입니다.
 * Handles user authentication requests such as login and logout.
 * Endpoints:
 *  - POST /auth: User login
 *  - DELETE /auth: User logout
 */
@RestController
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/auth")
    public ResponseEntity<ApiResponse<Void>> login(@RequestBody AuthRequest request) {
        UUID sessionId = authService.login(request);

        ResponseCookie cookie = ResponseCookie.from("sessionId", sessionId.toString())
                .httpOnly(true)
                .path("/")
                .maxAge(432000)
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(ApiResponse.success("login_success", null));
    }

    @RequireAuth
    @DeleteMapping("/auth")
    public ResponseEntity<ApiResponse<Void>> logout(@CookieValue("sessionId") UUID sessionId) {
        authService.logout(sessionId);
        return ResponseEntity.noContent().build();
    }
}
