package gguip1.community.domain.image.controller;

import gguip1.community.domain.image.dto.ImageResponse;
import gguip1.community.global.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ImageController {
    @PostMapping("/images/profile-img")
    public ResponseEntity<ApiResponse<ImageResponse>> uploadProfileImage() {
        return ResponseEntity.ok(ApiResponse.success("Profile image uploaded", null));
    }

    @PostMapping("/images/post-img")
    public ResponseEntity<ApiResponse<ImageResponse>> uploadPostImage() {
        return ResponseEntity.ok(ApiResponse.success("Post image uploaded", null));
    }
}
