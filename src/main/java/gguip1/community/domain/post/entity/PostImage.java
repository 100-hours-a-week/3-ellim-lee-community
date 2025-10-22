package gguip1.community.domain.post.entity;

import gguip1.community.domain.image.entity.Image;
import gguip1.community.domain.post.id.PostImageId;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "post_images")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class PostImage {
    @EmbeddedId
    private PostImageId postImageId;

    @ManyToOne
    @MapsId("postId")
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @ManyToOne
    @MapsId("imageId")
    @JoinColumn(name = "image_id", nullable = false)
    private Image image;

    @Column(name = "image_order", nullable = false)
    private Byte imageOrder;

    @Builder
    public PostImage(PostImageId postImageId, Post post, Image image, byte imageOrder){
        this.postImageId = postImageId;
        this.post = post;
        this.image = image;
        this.imageOrder = imageOrder;
    }
}
