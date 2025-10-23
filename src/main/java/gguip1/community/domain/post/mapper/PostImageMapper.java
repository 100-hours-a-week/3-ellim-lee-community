package gguip1.community.domain.post.mapper;

import gguip1.community.domain.image.entity.Image;
import gguip1.community.domain.post.entity.Post;
import gguip1.community.domain.post.entity.PostImage;
import gguip1.community.domain.post.id.PostImageId;
import org.springframework.stereotype.Component;

@Component
public class PostImageMapper {
    public PostImage toEntity(Post post, Image image, byte imageOrder){
        return PostImage.builder()
                .postImageId(new PostImageId(post.getPostId(), image.getImageId()))
                .post(post)
                .image(image)
                .imageOrder(imageOrder)
                .build();
    }
}
