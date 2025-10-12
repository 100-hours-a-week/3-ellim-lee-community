package gguip1.community.domain.post.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "posts_stats")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
@Setter
public class PostStat {
    @Id
    @Column(name = "post_id")
    private Long postId;

    @OneToOne
    @MapsId
    @JoinColumn(name = "post_id", nullable = false, unique = true)
    private Post post;

    @Column(name = "view_count", nullable = false)
    @Builder.Default
    private Integer viewCount = 0;

    @Column(name = "like_count", nullable = false)
    @Builder.Default
    private Integer likeCount = 0;

    @Column(name = "comment_count", nullable = false)
    @Builder.Default
    private Integer commentCount = 0;

    public void incrementViewCount() {
        this.viewCount++;
    }

    public void incrementLikeCount() {
        this.likeCount++;
    }

    public void decrementLikeCount() {
        if (this.likeCount > 0) {
            this.likeCount--;
        }
    }

    public void incrementCommentCount() {
        this.commentCount++;
    }

    public void decrementCommentCount() {
        if (this.commentCount > 0) {
            this.commentCount--;
        }
    }
}
