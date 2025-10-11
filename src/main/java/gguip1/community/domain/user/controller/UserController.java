package gguip1.community.domain.user.controller;

import gguip1.community.domain.user.dto.UserPasswordUpdateRequest;
import gguip1.community.domain.user.dto.UserRequest;
import gguip1.community.domain.user.dto.UserResponse;
import gguip1.community.domain.user.dto.UserUpdateRequest;
import gguip1.community.global.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    @PostMapping("/users")
    public ResponseEntity<ApiResponse<Void>> createUser(@RequestBody UserRequest request) {
        return ResponseEntity.ok(new ApiResponse<>("Success", null, null));
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<ApiResponse<UserResponse>> getUser(@PathVariable Integer userId) {
        return ResponseEntity.ok(new ApiResponse<>("Success", new UserResponse("test-email@test.com", "username", null), null));
    }

    @PatchMapping("/users/{userId}")
    public ResponseEntity<ApiResponse<Void>> updateUser(@PathVariable Integer userId, @RequestBody UserUpdateRequest request) {
        return ResponseEntity.ok(new ApiResponse<>("Success", null, null));
    }

    @PatchMapping("/users/{userId}/password")
    public ResponseEntity<ApiResponse<Void>> updateUserPassword(@PathVariable Integer userId, @RequestBody UserPasswordUpdateRequest request) {
        return ResponseEntity.ok(new ApiResponse<>("Success", null, null));
    }

    @DeleteMapping("/users/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer userId) {
        return ResponseEntity.noContent().build();
    }
}
