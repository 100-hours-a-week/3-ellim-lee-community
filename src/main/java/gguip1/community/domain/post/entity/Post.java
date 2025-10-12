package gguip1.community.domain.post.entity;

import gguip1.community.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 게시물 정보를 나타내는 엔티티입니다.
 * Fields:
 *  post_id - 게시물의 고유 식별자 (Long, 자동 생성)
 *  user - 게시물을 작성한 사용자 (User 엔티티와 다대일 관계)
 *  title - 게시물 제목 (String)
 *  content - 게시물 내용 (String, TEXT 타입)
 *  status - 게시물 상태 (Byte), 0: 활성, 1: 비활성
 *  createdAt - 게시물 생성 시간 (LocalDateTime)
 *  updatedAt - 게시물 수정 시간 (LocalDateTime)
 *  deletedAt - 게시물 삭제 시간 (LocalDateTime, null 가능)
 */
@Entity
@Table(name = "posts")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Post {
    @Id
    @Column(name = "post_id", nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "title", nullable = false)
    private String title;

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

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PostImage> postImages;
}
