package gguip1.community.domain.post.entity;

import gguip1.community.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * 게시물 댓글 정보를 나타내는 엔티티입니다.
 * Fields:
 *  commentId - 댓글의 고유 식별자 (Long, 자동 생성)
 *  user - 댓글을 작성한 사용자 (User 엔티티와 다대일 관계)
 *  post - 댓글이 속한 게시물 (Post 엔티티와 다대일 관계)
 *  content - 댓글 내용 (String, TEXT 타입)
 *  status - 댓글 상태 (Byte), 0: 활성, 1: 비활성
 *  createdAt - 댓글 생성 시간 (LocalDateTime)
 *  updatedAt - 댓글 수정 시간 (LocalDateTime)
 *  deletedAt - 댓글 삭제 시간 (LocalDateTime, null 가능)
 */
@Entity
@Table(name = "post_comments")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
@Setter
public class PostComment {
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

    @Builder.Default
    @Column(name = "status", nullable = false)
    private Byte status = 0;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;
}
