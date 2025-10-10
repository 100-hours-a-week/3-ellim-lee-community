package gguip1.community.domain.post.id;

import gguip1.community.domain.post.entity.Post;
import gguip1.community.domain.user.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * PostLikeId는 게시물 좋아요(PostLike) 엔티티의 복합 기본 키를 나타내는 임베디드 ID 클래스입니다.
 * 이 클래스는 사용자(User)와 게시물(Post) 간의 좋아요 관계를 고유하게 식별하는 데 사용됩니다.
 * Fields:
 *  user - 좋아요를 누른 사용자 (User 엔티티)
 *  post - 좋아요가 눌린 게시물 (Post 엔티티)
 */
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
public class PostLikeId implements Serializable {
    @Column(name = "user_id", nullable = false)
    private User user;

    @Column(name = "post_id", nullable = false)
    private Post post;
}
