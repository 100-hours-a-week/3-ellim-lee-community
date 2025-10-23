package gguip1.community.domain.post.mapper;

import gguip1.community.domain.post.dto.AuthorDto;
import gguip1.community.domain.post.dto.PostCommentPageItemResponse;
import gguip1.community.domain.post.dto.PostCommentPageResponse;
import gguip1.community.domain.post.entity.Post;
import gguip1.community.domain.post.entity.PostComment;
import gguip1.community.domain.user.entity.User;
import org.springframework.stereotype.Component;

@Component
public class PostCommentMapper {

    public PostCommentPageItemResponse toPostCommentPageItemResponse(PostComment postComment, User user, boolean isAuthor){
        return PostCommentPageItemResponse.builder()
                .commentId(postComment.getCommentId())
                .author(
                        new AuthorDto(
                                user.getNickname(),
                                user.getProfileImage() != null ? user.getProfileImage().getUrl() : null
                        )
                )
                .content(postComment.getContent())
                .isAuthor(isAuthor)
                .build();
    }

    public PostComment toEntity(User user, Post post, String content){
        return PostComment.builder()
                .user(user)
                .post(post)
                .content(content)
                .build();
    }

    public PostCommentPageItemResponse toPostCommentPageItemResponse(PostComment postComment, boolean isAuthor){
        User user = postComment.getUser();
        return PostCommentPageItemResponse.builder()
                .commentId(postComment.getCommentId())
                .content(postComment.getContent())
                .author(new AuthorDto(user.getNickname(),
                        user.getProfileImage() != null ? user.getProfileImage().getUrl() : null))
                .isAuthor(isAuthor)
                .build();
    }
}
