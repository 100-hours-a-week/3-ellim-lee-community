package gguip1.community.domain.user.entity;

import gguip1.community.domain.image.entity.Image;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 사용자 정보를 나타내는 엔티티입니다.
 * Fields:
 *  userId - 사용자의 고유 식별자 (Integer, 자동 생성)
 *  profileImage - 사용자의 프로필 이미지 (Image 엔티티와 일대일 관계)
 *  email - 사용자의 이메일 주소 (String, 고유)
 *  password - 사용자의 비밀번호 (String)
 *  nickname - 사용자의 닉네임 (String, 고유)
 *  status - 사용자의 상태 (Byte), 0: 활성, 1: 비활성
 *  createdAt - 사용자 생성 시간 (LocalDateTime)
 *  updatedAt - 사용자 정보 수정 시간 (LocalDateTime)
 *  deletedAt - 사용자 삭제 시간 (LocalDateTime, null 가능)
 */
@Entity
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {
    @Id
    @Column(name = "user_id", nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    @OneToOne
    @JoinColumn(name = "profile_image_id")
    private Image profileImage;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "nickname", nullable = false, unique = true)
    private String nickname;

    @Column(name = "status", nullable = false)
    private Byte status = 0;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;
}
