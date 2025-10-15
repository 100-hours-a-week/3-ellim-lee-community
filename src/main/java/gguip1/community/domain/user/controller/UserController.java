package gguip1.community.domain.user.controller;

import gguip1.community.domain.auth.entity.Session;
import gguip1.community.domain.user.dto.UserCreateRequest;
import gguip1.community.domain.user.dto.UserPasswordUpdateRequest;
import gguip1.community.domain.user.dto.UserResponse;
import gguip1.community.domain.user.dto.UserUpdateRequest;
import gguip1.community.domain.user.service.UserService;
import gguip1.community.global.annotation.RequireAuth;
import gguip1.community.global.response.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/users")
    public ResponseEntity<ApiResponse<Void>> createUser(@Valid @RequestBody UserCreateRequest request) {
        userService.createUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("Registration successful", null));
    }

    @RequireAuth
    @GetMapping("/users/me")
    public ResponseEntity<ApiResponse<UserResponse>> getMyInfo(HttpServletRequest request) {
        Session session = (Session) request.getAttribute("session");
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success("get_user_success", userService.getMyInfo(session)));
    }

    @RequireAuth
    @GetMapping("/users/{userId}")
    public ResponseEntity<ApiResponse<UserResponse>> getUser(@PathVariable Long userId) {
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success("get_user_success", userService.getUser(userId)));
    }

    @RequireAuth
    @PatchMapping("/users/me")
    public ResponseEntity<ApiResponse<Void>> updateMyInfo(HttpServletRequest request, @RequestBody UserUpdateRequest requestBody) {
        Session session = (Session) request.getAttribute("session");
        userService.updateMyInfo(session, requestBody);
        return ResponseEntity.noContent().build();
    }

    @RequireAuth
    @PatchMapping("/users/{userId}")
    public ResponseEntity<ApiResponse<Void>> updateUser(@PathVariable Long userId, @RequestBody UserUpdateRequest request) {
        userService.updateUser(userId, request);
        return ResponseEntity.noContent().build();
    }

    @RequireAuth
    @PatchMapping("/users/me/password")
    public ResponseEntity<ApiResponse<Void>> updateMyPassword(HttpServletRequest request, @RequestBody UserPasswordUpdateRequest requestBody) {
        Session session = (Session) request.getAttribute("session");
        userService.updateMyPassword(session, requestBody);
        return ResponseEntity.noContent().build();
    }

    @RequireAuth
    @PatchMapping("/users/{userId}/password")
    public ResponseEntity<ApiResponse<Void>> updateUserPassword(@PathVariable Long userId, @RequestBody UserPasswordUpdateRequest request) {
        userService.updateUserPassword(userId, request);
        return ResponseEntity.noContent().build();
    }

    @RequireAuth
    @DeleteMapping("/users/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }

    @RequireAuth
    @DeleteMapping("/users/me")
    public ResponseEntity<Void> deleteMyAccount(HttpServletRequest request) {
        Session session = (Session) request.getAttribute("session");
        userService.deleteUser(session.getUserId());
        return ResponseEntity.noContent().build();
    }
}
