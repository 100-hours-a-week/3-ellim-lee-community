package gguip1.community.domain.post.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
public class PostCommentPageItemResponse {
    private Long commentId;
    private String content;
    private AuthorDto author;
    private LocalDateTime createdAt;
    private Boolean isAuthor;
}
