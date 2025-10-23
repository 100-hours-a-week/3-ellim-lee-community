package gguip1.community.domain.post.mapper;

import gguip1.community.domain.post.dto.PostRequest;
import gguip1.community.domain.post.entity.Post;
import gguip1.community.domain.post.entity.PostImage;
import gguip1.community.domain.user.entity.User;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PostMapper {
    public Post fromPostRequest(PostRequest postRequest, User user){
        return Post.builder()
                .user(user)
                .title(postRequest.title())
                .content(postRequest.content())
                .build();
    }
}
