package gguip1.community.domain.post.repository;

import gguip1.community.domain.post.entity.PostStat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PostStatRepository extends JpaRepository<PostStat, Long> {
    @Modifying
    @Query("UPDATE PostStat ps SET ps.likeCount = ps.likeCount + 1 WHERE ps.postId = :postId")
    void incrementLikeCount(@Param("postId") Long postId);

    @Modifying
    @Query("UPDATE PostStat ps SET ps.likeCount = ps.likeCount - 1 WHERE ps.postId = :postId AND ps.likeCount > 0")
    void decrementLikeCount(@Param("postId") Long postId);

    @Modifying
    @Query("UPDATE PostStat ps SET ps.commentCount = ps.commentCount + 1 WHERE ps.postId = :postId")
    void incrementCommentCount(@Param("postId") Long postId);

    @Modifying
    @Query("UPDATE PostStat ps SET ps.commentCount = ps.commentCount - 1 WHERE ps.postId = :postId AND ps.commentCount > 0")
    void decrementCommentCount(@Param("postId") Long postId);

    @Modifying
    @Query("UPDATE PostStat ps SET ps.viewCount = ps.viewCount + 1 WHERE ps.postId = :postId")
    void incrementViewCount(@Param("postId") Long postId);
}
