package gguip1.community.domain.auth.controller;

import gguip1.community.domain.auth.dto.AuthRequest;
import gguip1.community.global.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    @PostMapping("/auth")
    public ResponseEntity<ApiResponse<Void>> login(@RequestBody AuthRequest request) {
        return ResponseEntity.ok(new ApiResponse<>("Success", null, null));
    }

    @DeleteMapping("/auth")
    public ResponseEntity<ApiResponse<Void>> logout() {
        return ResponseEntity.ok(new ApiResponse<>("Success", null, null));
    }
}
