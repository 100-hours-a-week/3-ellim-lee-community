package gguip1.community.domain.post.controller;

import gguip1.community.domain.post.service.PostLikeService;
import gguip1.community.global.response.ApiResponse;
import gguip1.community.global.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PostLikeController {
    private final PostLikeService postLikeService;

    @PostMapping("/posts/{postId}/like")
    public ResponseEntity<ApiResponse<Void>> likePost(@AuthenticationPrincipal CustomUserDetails user, @PathVariable Long postId) {
        postLikeService.createLike(user.getUserId(), postId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/posts/{postId}/like")
    public ResponseEntity<ApiResponse<Void>> unlikePost(@AuthenticationPrincipal CustomUserDetails user, @PathVariable Long postId) {
        postLikeService.deleteLike(user.getUserId(), postId);
        return ResponseEntity.noContent().build();
    }
}
