package gguip1.community.domain.auth.controller;

import gguip1.community.domain.auth.dto.AuthRequest;
import gguip1.community.domain.auth.service.AuthService;
import gguip1.community.global.response.ApiResponse;
import org.springframework.http.HttpHeaders;
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
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/auth")
    public ResponseEntity<ApiResponse<Void>> login(@RequestBody AuthRequest request) {
        UUID sessionId = authService.login(request);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Set-Cookie", "sessionId=" + sessionId + "; HttpOnly; Path=/; Max-Age=432000");

        return ResponseEntity.ok()
                .headers(headers)
                .body(ApiResponse.success("login_success", null));
    }

    @DeleteMapping("/auth")
    public ResponseEntity<ApiResponse<Void>> logout(@CookieValue("sessionId") UUID sessionId) {
        authService.logout(sessionId);
        return ResponseEntity.noContent().build();
    }
}
