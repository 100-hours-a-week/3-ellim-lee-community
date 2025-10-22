package gguip1.community.domain.post.entity;

import gguip1.community.domain.post.id.PostLikeId;
import gguip1.community.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Table(name = "post_likes")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
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

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Builder
    public PostLike(PostLikeId postLikeId, User user, Post post){
        this.postLikeId = postLikeId;
        this.user = user;
        this.post = post;
    }
}
