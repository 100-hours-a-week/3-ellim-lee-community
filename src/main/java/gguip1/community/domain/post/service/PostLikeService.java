package gguip1.community.domain.post.service;

import gguip1.community.domain.post.entity.Post;
import gguip1.community.domain.post.entity.PostLike;
import gguip1.community.domain.post.id.PostLikeId;
import gguip1.community.domain.post.mapper.PostLikeMapper;
import gguip1.community.domain.post.repository.PostLikeRepository;
import gguip1.community.domain.post.repository.PostRepository;
import gguip1.community.domain.post.repository.PostStatRepository;
import gguip1.community.domain.user.entity.User;
import gguip1.community.domain.user.repository.UserRepository;
import gguip1.community.global.exception.ErrorCode;
import gguip1.community.global.exception.ErrorException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostLikeService {
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final PostLikeRepository postLikeRepository;
    private final PostStatRepository postStatRepository;


    private final PostLikeMapper postLikeMapper;

    @Transactional
    public void createLike(Long userId, Long postId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ErrorException(ErrorCode.USER_NOT_FOUND));
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ErrorException(ErrorCode.NOT_FOUND));


        if (postLikeRepository.existsById(new PostLikeId(userId, postId))) {
            throw new ErrorException(ErrorCode.DUPLICATE_LIKE);
        }

        PostLikeId postLikeId = new PostLikeId(userId, postId);
        PostLike postLike = postLikeMapper.toEntity(postLikeId, post, user);

        postLikeRepository.save(postLike);
        postStatRepository.incrementLikeCount(postId);
    }

    @Transactional
    public void deleteLike(Long userId, Long postId) {
        PostLikeId postLikeId = new PostLikeId(userId, postId);

        if (!postLikeRepository.existsById(postLikeId)) {
            throw new ErrorException(ErrorCode.NOT_FOUND);
        }

        postLikeRepository.deleteById(postLikeId);
        postStatRepository.decrementLikeCount(postId);
    }

}
