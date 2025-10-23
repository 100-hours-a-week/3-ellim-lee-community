package gguip1.community.domain.post.service;

import gguip1.community.domain.post.dto.PostCommentPageItemResponse;
import gguip1.community.domain.post.dto.PostCommentPageResponse;
import gguip1.community.domain.post.dto.PostCommentRequest;
import gguip1.community.domain.post.entity.Post;
import gguip1.community.domain.post.entity.PostComment;
import gguip1.community.domain.post.mapper.PostCommentMapper;
import gguip1.community.domain.post.repository.PostCommentRepository;
import gguip1.community.domain.post.repository.PostRepository;
import gguip1.community.domain.post.repository.PostStatRepository;
import gguip1.community.domain.user.entity.User;
import gguip1.community.domain.user.repository.UserRepository;
import gguip1.community.global.exception.ErrorCode;
import gguip1.community.global.exception.ErrorException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostCommentService {
    private final PostCommentRepository postCommentRepository;
    private final PostStatRepository postStatRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    private final PostCommentMapper postCommentMapper;

    @Transactional
    public PostCommentPageResponse getComments(Long userId, Long postId, Long lastCommentId, int size){
        List<PostComment> postComments =
                lastCommentId == null
                        ? postCommentRepository.findFirstPageByPostId(postId,size + 1)
                        : postCommentRepository.findNextPageByPostId(postId, lastCommentId, size + 1);

        boolean hasNext = postComments.size() > size;

        List<PostCommentPageItemResponse> commentPageItemResponses = postComments.stream()
                .limit(size)
                .map(postComment -> {
                    User user = postComment.getUser();
                    boolean isAuthor = userId.equals(postComment.getUser().getUserId());

                    return postCommentMapper.toPostCommentPageItemResponse(postComment, user, isAuthor);
                }).toList();

        Long newLastCommentId = commentPageItemResponses.isEmpty() ? null :
                commentPageItemResponses.getLast().commentId();

        return new PostCommentPageResponse(
                commentPageItemResponses,
                hasNext,
                newLastCommentId
        );
    }

    @Transactional
    public PostCommentPageItemResponse createComment(Long userId, Long postId, PostCommentRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ErrorException(ErrorCode.USER_NOT_FOUND));
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ErrorException(ErrorCode.NOT_FOUND));

        postStatRepository.incrementCommentCount(postId);

        PostComment postComment = postCommentMapper.toEntity(user, post, request.content());

        postCommentRepository.save(postComment);

        return postCommentMapper.toPostCommentPageItemResponse(postComment, true);
    }

    @Transactional
    public PostCommentPageItemResponse updateComment(Long userId, Long postId, Long commentId, PostCommentRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ErrorException(ErrorCode.USER_NOT_FOUND));
        PostComment postComment = postCommentRepository.findById(commentId)
                .orElseThrow(() -> new ErrorException(ErrorCode.NOT_FOUND));

        if (!postComment.getUser().getUserId().equals(user.getUserId())) {
            throw new ErrorException(ErrorCode.ACCESS_DENIED);
        }

        postComment.updateComment(request.content());

        postCommentRepository.save(postComment);

        return postCommentMapper.toPostCommentPageItemResponse(postComment, true);
    }

    @Transactional
    public void deleteComment(Long userId, Long postId, Long commentId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ErrorException(ErrorCode.USER_NOT_FOUND));
        PostComment postComment = postCommentRepository.findById(commentId)
                .orElseThrow(() -> new ErrorException(ErrorCode.NOT_FOUND));

        if (!postComment.getUser().getUserId().equals(user.getUserId())) {
            throw new ErrorException(ErrorCode.ACCESS_DENIED);
        }

        postStatRepository.decrementCommentCount(postComment.getPost().getPostId());

        postCommentRepository.delete(postComment);
    }
}
