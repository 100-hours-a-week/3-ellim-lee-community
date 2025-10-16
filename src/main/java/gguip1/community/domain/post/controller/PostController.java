package gguip1.community.domain.post.controller;

import gguip1.community.domain.auth.entity.Session;
import gguip1.community.domain.post.dto.*;
import gguip1.community.domain.post.service.PostService;
import gguip1.community.global.response.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @PostMapping("/posts")
    public ResponseEntity<ApiResponse<Void>> createPost(@RequestBody PostRequest postRequest, HttpServletRequest request) {
        Session session = (Session) request.getAttribute("session");
        postService.createPost(session, postRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponse.success("Post created", null)
        );
    }

    @GetMapping("/posts")
    public ResponseEntity<ApiResponse<PostPageResponse>> getPosts(
            @RequestParam Optional<Long> lastPostId
    ){
        PostPageResponse response = postService.getPosts(lastPostId.orElse(null), 5);
        return ResponseEntity.status(HttpStatus.OK).body(
                ApiResponse.success("Posts retrieved successfully", response)
        );
    }

    @GetMapping("/posts/{postId}")
    public ResponseEntity<ApiResponse<PostDetailResponse>> getPost(@PathVariable Long postId, HttpServletRequest request) {
        Session session = (Session) request.getAttribute("session");
        return ResponseEntity.status(HttpStatus.OK).body(
                ApiResponse.success("Posts retrieved successfully", postService.getPostDetail(postId, session))
        );
    }

    @PatchMapping("/posts/{postId}")
    public ResponseEntity<ApiResponse<Void>> updatePost(@PathVariable Long postId, @RequestBody PostUpdateRequest postUpdateRequest, HttpServletRequest request) {
        Session session = (Session) request.getAttribute("session");
        postService.updatePost(session, postId, postUpdateRequest);
        return ResponseEntity.ok(ApiResponse.success("Post updated", null));
    }

    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<ApiResponse<Void>> deletePost(@PathVariable Long postId, HttpServletRequest request) {
        Session session = (Session) request.getAttribute("session");
        postService.deletePost(session, postId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/posts/{postId}/like")
    public ResponseEntity<ApiResponse<Void>> likePost(@PathVariable Long postId, HttpServletRequest request) {
        Session session = (Session) request.getAttribute("session");
        postService.createLike(session, postId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/posts/{postId}/like")
    public ResponseEntity<ApiResponse<Void>> unlikePost(@PathVariable Long postId, HttpServletRequest request) {
        Session session = (Session) request.getAttribute("session");
        postService.deleteLike(session, postId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/posts/{postId}/comments")
    public ResponseEntity<ApiResponse<PostCommentPageResponse>> getComments(@PathVariable Long postId,
                                                                        @RequestParam(required = false) Long lastCommentId,
                                                                        @RequestParam(defaultValue = "10") int size,
                                                                            HttpServletRequest request) {
        Session session = (Session) request.getAttribute("session");
        PostCommentPageResponse response = postService.getComments(session, postId, lastCommentId, size);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success("Comments retrieved successfully", response));
    }

    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<ApiResponse<Void>> createComment(@PathVariable Long postId, @RequestBody PostCommentRequest postCommentRequest, HttpServletRequest request) {
        Session session = (Session) request.getAttribute("session");
        postService.createComment(session, postId, postCommentRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("Comment created", null));
    }

    @PatchMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<ApiResponse<Void>> updateComment(@PathVariable Long postId, @PathVariable Long commentId, @RequestBody PostCommentRequest postCommentRequest, HttpServletRequest request) {
        Session session = (Session) request.getAttribute("session");
        postService.updateComment(session, postId, commentId, postCommentRequest);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<ApiResponse<Void>> deleteComment(@PathVariable Long postId, @PathVariable Long commentId, HttpServletRequest request) {
        Session session = (Session) request.getAttribute("session");
        postService.deleteComment(session, postId, commentId);
        return ResponseEntity.noContent().build();
    }
}
