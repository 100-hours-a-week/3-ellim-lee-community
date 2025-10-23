package gguip1.community.domain.post.controller;

import gguip1.community.domain.auth.entity.Session;
import gguip1.community.domain.post.dto.PostCommentPageResponse;
import gguip1.community.domain.post.dto.PostCommentRequest;
import gguip1.community.domain.post.service.PostCommentService;
import gguip1.community.global.response.ApiResponse;
import gguip1.community.global.security.CustomUserDetails;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class PostCommentController {
    private final PostCommentService postCommentService;

    @GetMapping("/posts/{postId}/comments")
    public ResponseEntity<ApiResponse<PostCommentPageResponse>> getComments(@AuthenticationPrincipal CustomUserDetails user,
                                                                            @PathVariable Long postId,
                                                                            @RequestParam(required = false) Long lastCommentId,
                                                                            @RequestParam(defaultValue = "10") int size) {

        PostCommentPageResponse response = postCommentService.getComments(user.getUserId(), postId, lastCommentId, size);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success("Comments retrieved successfully", response));
    }

    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<ApiResponse<Void>> createComment(@AuthenticationPrincipal CustomUserDetails user,
                                                           @PathVariable Long postId,
                                                           @RequestBody PostCommentRequest postCommentRequest) {
        postCommentService.createComment(user.getUserId(), postId, postCommentRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("Comment created", null));
    }

    @PatchMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<ApiResponse<Void>> updateComment(@AuthenticationPrincipal CustomUserDetails user,
                                                           @PathVariable Long postId,
                                                           @PathVariable Long commentId,
                                                           @RequestBody PostCommentRequest postCommentRequest) {
        postCommentService.updateComment(user.getUserId(), postId, commentId, postCommentRequest);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<ApiResponse<Void>> deleteComment(@AuthenticationPrincipal CustomUserDetails user,
                                                           @PathVariable Long postId,
                                                           @PathVariable Long commentId) {
        postCommentService.deleteComment(user.getUserId(), postId, commentId);
        return ResponseEntity.noContent().build();
    }
}
