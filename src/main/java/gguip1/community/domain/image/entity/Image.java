package gguip1.community.domain.image.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "images")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
public class Image {
    @Id
    @Column(name = "image_id", nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long imageId;

    @Column(name = "url", nullable = false)
    private String url;

    @Builder.Default
    @Column(name = "status", nullable = false)
    private Byte status = 0;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "orphaned_at")
    private LocalDateTime orphanedAt;
}
