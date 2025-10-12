package gguip1.community.domain.post.entity;

import gguip1.community.domain.post.id.PostLikeId;
import gguip1.community.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 게시물 좋아요 정보를 나타내는 엔티티입니다.
 * Fields:
 *  postLikeId - 복합 기본 키 (PostLikeId 임베디드 ID 클래스)
 *  user - 좋아요를 누른 사용자 (User 엔티티와 다대일 관계)
 *  post - 좋아요가 눌린 게시물 (Post 엔티티와 다대일 관계)
 *  createdAt - 좋아요 생성 시간 (LocalDateTime)
 */
@Entity
@Table(name = "post_likes")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class PostLike {
    @EmbeddedId
    private PostLikeId postLikeId;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @MapsId("postId")
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
}
