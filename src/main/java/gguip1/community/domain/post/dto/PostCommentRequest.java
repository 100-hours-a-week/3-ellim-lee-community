package gguip1.community.domain.post.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PostCommentRequest {
    @NotBlank
    private String content;
}
