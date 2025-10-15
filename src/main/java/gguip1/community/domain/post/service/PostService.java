package gguip1.community.domain.post.service;

import gguip1.community.domain.auth.entity.Session;
import gguip1.community.domain.image.entity.Image;
import gguip1.community.domain.image.repository.ImageRepository;
import gguip1.community.domain.post.dto.*;
import gguip1.community.domain.post.entity.*;
import gguip1.community.domain.post.id.PostImageId;
import gguip1.community.domain.post.id.PostLikeId;
import gguip1.community.domain.post.repository.PostCommentRepository;
import gguip1.community.domain.post.repository.PostImageRepository;
import gguip1.community.domain.post.repository.PostLikeRepository;
import gguip1.community.domain.post.repository.PostRepository;
import gguip1.community.domain.user.entity.User;
import gguip1.community.domain.user.repository.UserRepository;
import gguip1.community.global.exception.ErrorCode;
import gguip1.community.global.exception.ErrorException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final ImageRepository imageRepository;
    private final PostImageRepository postImageRepository;
    private final UserRepository userRepository;
    private final PostLikeRepository postLikeRepository;
    private final PostCommentRepository postCommentRepository;

    @Transactional
    public void createPost(Session session, PostRequest postRequest) {
        List<Image> images = Collections.emptyList();
        if (postRequest.getImageIds() != null && !postRequest.getImageIds().isEmpty()) {
            images = imageRepository.findAllById(postRequest.getImageIds());
        }

        User user = userRepository.findById(session.getUserId())
                .orElseThrow(() -> new ErrorException(ErrorCode.USER_NOT_FOUND));

        Post post = Post.builder()
                .user(user)
                .title(postRequest.getTitle())
                .content(postRequest.getContent())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        PostStat postStat = PostStat.builder()
                .post(post)
                .build();

        post.setPostStat(postStat);
        postRepository.save(post);

        AtomicInteger order = new AtomicInteger(0);
        List<PostImage> postImages = images.stream()
                .map(image -> {
                    PostImageId postImageId = new PostImageId(post.getPostId(), image.getImageId());
                    return PostImage.builder()
                            .postImageId(postImageId)
                            .post(post)
                            .image(image)
                            .imageOrder((byte) order.getAndIncrement())
                            .build();
                })
                .toList();
        postImageRepository.saveAll(postImages);
    }

    @Transactional
    public void createLike(Session session, Long postId) {
        User user = userRepository.findById(session.getUserId())
                .orElseThrow(() -> new ErrorException(ErrorCode.USER_NOT_FOUND));
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ErrorException(ErrorCode.NOT_FOUND));

        if (postLikeRepository.existsById(new PostLikeId(session.getUserId(), postId))) {
            throw new ErrorException(ErrorCode.DUPLICATE_LIKE);
        }

        PostLikeId postLikeId = new PostLikeId(session.getUserId(), postId);
        PostLike postLike = PostLike.builder()
                .postLikeId(postLikeId)
                .post(post)
                .user(user)
                .createdAt(LocalDateTime.now())
                .build();

        post.getPostStat().incrementLikeCount();
        postLikeRepository.save(postLike);
        postRepository.save(post);
    }

    @Transactional
    public void deleteLike(Session session, Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ErrorException(ErrorCode.NOT_FOUND));

        PostLikeId postLikeId = new PostLikeId(session.getUserId(), postId);

        if (!postLikeRepository.existsById(postLikeId)) {
            throw new ErrorException(ErrorCode.NOT_FOUND);
        }

        post.getPostStat().decrementLikeCount();
        postLikeRepository.deleteById(postLikeId);
        postRepository.save(post);
    }

    @Transactional
    public PostPageResponse getPosts(Long lastPostId, int pageSize) {
        List<Post> posts;

        if (lastPostId == null){
            posts = postRepository.findFirstPage(pageSize + 1);
        } else {
            posts = postRepository.findNextPage(lastPostId, pageSize + 1);
        }

        boolean hasNext = posts.size() > pageSize;

        List<PostPageItemResponse> postPageItemResponses = posts.stream()
                .limit(pageSize)
                .map(post -> {
                    User user = post.getUser();
                    Long profileImageId = user.getProfileImage() != null
                            ? user.getProfileImage().getImageId()
                            : null;

                    List<String> imageUrls = post.getPostImages().stream()
                            .map(postImage -> postImage.getImage().getUrl())
                            .toList();

                    return PostPageItemResponse.builder()
                            .postId(post.getPostId())
                            .imageUrls(imageUrls)
                            .title(post.getTitle())
                            .content(post.getContent())
                            .author(new AuthorDto(
                                    user.getNickname(),
                                    profileImageId
                            ))
                            .createdAt(post.getCreatedAt())
                            .likeCount(post.getPostStat().getLikeCount())
                            .commentCount(post.getPostStat().getCommentCount())
                            .viewCount(post.getPostStat().getViewCount())
                            .build();
                })
                .toList();

        Long newLastPostId = postPageItemResponses.isEmpty() ? null :
                postPageItemResponses.getLast().getPostId();

        return new PostPageResponse(
                postPageItemResponses,
                hasNext,
                newLastPostId
        );
    }

    @Transactional
    public PostDetailResponse getPostDetail(Long postId, Session session) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ErrorException(ErrorCode.NOT_FOUND));

        post.getPostStat().incrementViewCount();
        postRepository.save(post);

        User user = post.getUser();
        Long profileImageId = user.getProfileImage() != null
                ? user.getProfileImage().getImageId()
                : null;

        List<String> imageUrls = post.getPostImages().stream()
                .map(postImage -> postImage.getImage().getUrl())
                .toList();

        boolean isAuthor = session.getUserId() != null && session.getUserId().equals(user.getUserId());

        return PostDetailResponse.builder()
                .imageUrls(imageUrls)
                .title(post.getTitle())
                .content(post.getContent())
                .author(new AuthorDto(
                        user.getNickname(),
                        profileImageId
                ))
                .createdAt(post.getCreatedAt())
                .likeCount(post.getPostStat().getLikeCount())
                .commentCount(post.getPostStat().getCommentCount())
                .viewCount(post.getPostStat().getViewCount())
                .isAuthor(isAuthor)
                .isLiked(false)
                .build();
    }


    @Transactional
    public void updatePost(Session session, Long postId, PostUpdateRequest postUpdateRequest) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ErrorException(ErrorCode.NOT_FOUND));

        if (!post.getUser().getUserId().equals(session.getUserId())) {
            throw new ErrorException(ErrorCode.ACCESS_DENIED);
        }

        post.setTitle(postUpdateRequest.getTitle());
        post.setContent(postUpdateRequest.getContent());
        post.setUpdatedAt(LocalDateTime.now());

        postRepository.save(post);

        postImageRepository.deleteAllByPost_PostId(postId);

        List<Image> images = Collections.emptyList();
        if (postUpdateRequest.getImageIds() != null && !postUpdateRequest.getImageIds().isEmpty()) {
            images = imageRepository.findAllById(postUpdateRequest.getImageIds());
        }

        AtomicInteger order = new AtomicInteger(0);
        List<PostImage> postImages = images.stream()
                .map(image -> {
                    PostImageId postImageId = new PostImageId(post.getPostId(), image.getImageId());
                    return PostImage.builder()
                            .postImageId(postImageId)
                            .post(post)
                            .image(image)
                            .imageOrder((byte) order.getAndIncrement())
                            .build();
                })
                .toList();
        postImageRepository.saveAll(postImages);
    }

    @Transactional
    public void deletePost(Session session, Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ErrorException(ErrorCode.NOT_FOUND));

        if (!post.getUser().getUserId().equals(session.getUserId())) {
            throw new ErrorException(ErrorCode.ACCESS_DENIED);
        }

        postRepository.delete(post);
    }

    @Transactional
    public PostCommentPageResponse getComments(Session session, Long postId, Long lastCommentId, int size) {
        List<PostComment> comments;

        if (lastCommentId == null){
            comments = postCommentRepository.findFirstPageByPostId(postId,size + 1);
        } else {
            comments = postCommentRepository.findNextPageByPostId(postId, lastCommentId, size + 1);
        }

        boolean hasNext = comments.size() > size;

        List<PostCommentPageItemResponse> commentResponses = comments.stream()
                .limit(size)
                .map(comment -> {
                    User user = comment.getUser();
                    Long profileImageId = user.getProfileImage() != null
                            ? user.getProfileImage().getImageId()
                            : null;

                    Boolean isAuthor = session.getUserId().equals(comment.getUser().getUserId());

                    return PostCommentPageItemResponse.builder()
                            .commentId(comment.getCommentId())
                            .author(new AuthorDto(
                                    user.getNickname(),
                                    profileImageId
                            ))
                            .content(comment.getContent())
                            .isAuthor(isAuthor)
                            .createdAt(comment.getCreatedAt())
                            .build();
                })
                .toList();

        Long newLastCommentId = commentResponses.isEmpty() ? null :
                commentResponses.getLast().getCommentId();

        return new PostCommentPageResponse(
                commentResponses,
                hasNext,
                newLastCommentId
        );
    }

    @Transactional
    public void createComment(Session session, Long postId, PostCommentRequest request) {
        User user = userRepository.findById(session.getUserId())
                .orElseThrow(() -> new ErrorException(ErrorCode.USER_NOT_FOUND));
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ErrorException(ErrorCode.NOT_FOUND));
        post.getPostStat().incrementCommentCount();
        postRepository.save(post);

        PostComment postComment = PostComment.builder()
                .user(user)
                .post(post)
                .content(request.getContent())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        postCommentRepository.save(postComment);
    }

    @Transactional
    public void updateComment(Session session, Long postId, Long commentId, PostCommentRequest request) {
        User user = userRepository.findById(session.getUserId())
                .orElseThrow(() -> new ErrorException(ErrorCode.USER_NOT_FOUND));
        PostComment postComment = postCommentRepository.findById(commentId)
                .orElseThrow(() -> new ErrorException(ErrorCode.NOT_FOUND));

        if (!postComment.getUser().getUserId().equals(user.getUserId())) {
            throw new ErrorException(ErrorCode.ACCESS_DENIED);
        }

        postComment.setContent(request.getContent());
        postComment.setUpdatedAt(LocalDateTime.now());

        postCommentRepository.save(postComment);
    }

    @Transactional
    public void deleteComment(Session session, Long postId, Long commentId) {
        User user = userRepository.findById(session.getUserId())
                .orElseThrow(() -> new ErrorException(ErrorCode.USER_NOT_FOUND));
        PostComment postComment = postCommentRepository.findById(commentId)
                .orElseThrow(() -> new ErrorException(ErrorCode.NOT_FOUND));

        Post post = postComment.getPost();
        post.getPostStat().decrementCommentCount();
        postRepository.save(post);

        if (!postComment.getUser().getUserId().equals(user.getUserId())) {
            throw new ErrorException(ErrorCode.ACCESS_DENIED);
        }

        postCommentRepository.delete(postComment);
    }
}
