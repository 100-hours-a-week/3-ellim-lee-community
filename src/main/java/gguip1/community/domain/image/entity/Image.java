package gguip1.community.domain.image.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 이미지 정보를 나타내는 엔티티입니다.
 * Fields:
 *  imageId - 이미지의 고유 식별자 (Long, 자동 생성)
 *  url - 이미지 URL (String)
 *  status - 이미지 상태 (Byte), 0: 활성, 1: 비활성
 *  createdAt - 이미지 생성 시간 (LocalDateTime)
 *  orphanedAt - 이미지 고아화 시간 (LocalDateTime, null 가능)
 */
@Entity
@Table(name = "images")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Image {
    @Id
    @Column(name = "image_id", nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long imageId;

    @Column(name = "url", nullable = false)
    private String url;

    @Column(name = "status", nullable = false)
    private Byte status = 0;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "orphaned_at")
    private LocalDateTime orphanedAt;
}
