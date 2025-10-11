package gguip1.community.domain.post.controller;

import gguip1.community.domain.post.dto.*;
import gguip1.community.global.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class PostController {
    @GetMapping("/posts")
    public ResponseEntity<ApiResponse<PostPageResponse>> getPosts(@RequestParam(required = false) Long lastPostId,
                                                                  @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(ApiResponse.success("Success", null));
    }

    @PostMapping("/posts")
    public ResponseEntity<ApiResponse<Void>> createPost(@RequestBody PostRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Post created", null));
    }

    @GetMapping("/posts/{postId}")
    public ResponseEntity<ApiResponse<PostDetailResponse>> getPost(@PathVariable Long postId) {
        return ResponseEntity.ok(ApiResponse.success("Success", null));
    }

    @PatchMapping("/posts/{postId}")
    public ResponseEntity<ApiResponse<Void>> updatePost(@PathVariable Long postId, @RequestBody PostUpdateRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Post updated", null));
    }

    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<ApiResponse<Void>> deletePost(@PathVariable Long postId) {
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/posts/{postId}/like")
    public ResponseEntity<ApiResponse<Void>> likePost(@PathVariable Long postId) {
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/posts/{postId}/like")
    public ResponseEntity<ApiResponse<Void>> unlikePost(@PathVariable Long postId) {
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/posts/{postId}/comments")
    public ResponseEntity<ApiResponse<PostCommentPageResponse>> getComments(@PathVariable Long postId,
                                                                        @RequestParam(required = false) Long lastCommentId,
                                                                        @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(ApiResponse.success("Success", null));
    }

    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<ApiResponse<Void>> createComment(@PathVariable Long postId, @RequestBody PostCommentRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Comment created", null));
    }

    @PatchMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<ApiResponse<Void>> updateComment(@PathVariable Long postId, @PathVariable Long commentId, @RequestBody PostCommentRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Comment updated", null));
    }

    @DeleteMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<ApiResponse<Void>> deleteComment(@PathVariable Long postId, @PathVariable Long commentId) {
        return ResponseEntity.noContent().build();
    }
}
