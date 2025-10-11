package gguip1.community.domain.auth.entity;

import gguip1.community.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * 인증에 사용되는 세션 정보를 나타내는 엔티티입니다.
 * Fields: 
 *  sessionId - 세션의 고유 식별자 (UUID)
 *  userId - 세션이 속한 사용자 ID (Long)
 *  expiresAt - 세션 만료 시간 (LocalDateTime)
 *  createdAt - 세션 생성 시간 (LocalDateTime)
 */
@Entity
@Table(name = "sessions")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Session {
    @Id
    @Column(name = "session_id", nullable = false, unique = true)
    private UUID sessionId;

    @Column(name = "user_id", nullable = false)
    private Integer userId;

    @Column(name = "expires_at", nullable = false)
    private LocalDateTime expiresAt;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    public boolean isExpired() {
        return expiresAt.isBefore(LocalDateTime.now());
    }
}
