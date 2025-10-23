package gguip1.community.domain.post.mapper;

import gguip1.community.domain.post.entity.Post;
import gguip1.community.domain.post.entity.PostLike;
import gguip1.community.domain.post.id.PostLikeId;
import gguip1.community.domain.user.entity.User;
import org.springframework.stereotype.Component;

@Component
public class PostLikeMapper {
    public PostLike toEntity (PostLikeId postLikeId, Post post, User user){
        return PostLike.builder()
                .postLikeId(postLikeId)
                .post(post)
                .user(user)
                .build();
    }
}
