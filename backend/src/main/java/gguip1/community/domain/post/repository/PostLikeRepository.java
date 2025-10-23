package gguip1.community.domain.post.repository;

import gguip1.community.domain.post.entity.PostLike;
import gguip1.community.domain.post.id.PostLikeId;
import gguip1.community.domain.post.service.PostLikeService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PostLikeRepository extends JpaRepository<PostLike, PostLikeId> {
    PostLike findByUserUserIdAndPostPostId(Integer userId, Long postId);
}
