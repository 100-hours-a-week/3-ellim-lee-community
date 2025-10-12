package gguip1.community.domain.image.controller;

import gguip1.community.domain.image.dto.ImageResponse;
import gguip1.community.domain.image.service.LocalStorageImageService;
import gguip1.community.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class ImageController {
    private final LocalStorageImageService imageService;

    @PostMapping("/images/profile-img")
    public ResponseEntity<ApiResponse<ImageResponse>> uploadProfileImage(
            @RequestParam("file") MultipartFile file
    ) throws IOException {
        ImageResponse response = imageService.upload(file);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("Profile image uploaded", response));
    }

    @PostMapping("/images/post-img")
    public ResponseEntity<ApiResponse<ImageResponse>> uploadPostImage(@RequestParam("file") MultipartFile file
    ) throws IOException {
        ImageResponse response = imageService.upload(file);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("Post image uploaded", response));
    }
}
