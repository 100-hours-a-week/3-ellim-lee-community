package gguip1.community.domain.post.id;

import gguip1.community.domain.image.entity.Image;
import gguip1.community.domain.post.entity.Post;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * PostImageId는 게시물 이미지(PostImage) 엔티티의 복합 기본 키를 나타내는 임베디드 ID 클래스입니다.
 * 이 클래스는 게시물(Post)과 이미지(Image) 간의 관계를 고유하게 식별하는 데 사용됩니다.
 * Fields:
 *  post - 게시물 (Post 엔티티)
 *  image - 이미지 (Image 엔티티)
 *  imageOrder - 이미지 순서 (Byte)
 */
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
public class PostImageId {
    @Column(name = "post_id", nullable = false)
    private Post post;

    @Column(name = "image_id", nullable = false)
    private Image image;

    @Column(name = "image_order", nullable = false)
    private Byte imageOrder;
}
