package gguip1.community.domain.auth.controller;

import gguip1.community.domain.auth.dto.AuthRequest;
import gguip1.community.domain.auth.dto.AuthResponse;
import gguip1.community.domain.auth.service.AuthService;
import gguip1.community.global.response.ApiResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/auth")
    public ResponseEntity<ApiResponse<AuthResponse>> login(@RequestBody AuthRequest request,
                                                           HttpServletRequest httpRequest) {
        AuthResponse authResponse = authService.login(request, httpRequest);

        return ResponseEntity.status(HttpStatus.OK).body(
                ApiResponse.success("login_success", authResponse)
        );
    }

    @DeleteMapping("/auth")
    public ResponseEntity<ApiResponse<Void>> logout(HttpServletRequest httpRequest,
                                                    HttpServletResponse httpResponse) {
        authService.logout(httpRequest);

        Cookie cookie = new Cookie("JSESSIONID", null);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        httpResponse.addCookie(cookie);

        return ResponseEntity.noContent().build();
    }
}
