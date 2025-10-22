package gguip1.community.domain.post.entity;

import gguip1.community.domain.user.entity.User;
import gguip1.community.global.entity.SoftDeleteEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "post_comments")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class PostComment extends SoftDeleteEntity {
    @Id
    @Column(name = "comment_id", nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    private String content;

    @Builder
    public PostComment(Long commentId, User user, Post post, String content){
        this.commentId = commentId;
        this.user = user;
        this.post = post;
        this.content = content;
    }

    public void updateComment(String content){
        this.content = content;
    }
}
