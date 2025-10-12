package gguip1.community.domain.post.repository;

import gguip1.community.domain.post.entity.PostLike;
import gguip1.community.domain.post.id.PostLikeId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostLikeRepository extends JpaRepository<PostLike, PostLikeId> {
    PostLike findByUserUserIdAndPostPostId(Integer userId, Long postId);
}
