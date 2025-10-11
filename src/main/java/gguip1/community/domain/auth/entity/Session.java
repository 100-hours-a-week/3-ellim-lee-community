package gguip1.community.domain.auth.entity;

import gguip1.community.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * 인증에 사용되는 세션 정보를 나타내는 엔티티입니다.
 * Fields: 
 *  sessionId - 세션의 고유 식별자 (UUID)
 *  user - 세션이 속한 사용자 (User 엔티티와 다대일 관계)
 *  expiresAt - 세션 만료 시간 (LocalDateTime)
 *  revokedAt - 조기 세션 만료 시간 (LocalDateTime)
 *  createdAt - 세션 생성 시간 (LocalDateTime)
 */
@Entity
@Table(name = "sessions")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Session {
    @Id
    @Column(name = "session_id", nullable = false, unique = true)
    private UUID sessionId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "expires_at", nullable = false)
    private LocalDateTime expiresAt;

    @Column(name = "revoked_at", nullable = false)
    private LocalDateTime revokedAt;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
}
