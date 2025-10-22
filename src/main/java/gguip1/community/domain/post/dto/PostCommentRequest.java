package gguip1.community.domain.post.dto;

import jakarta.validation.constraints.NotBlank;

public record PostCommentRequest(@NotBlank String content) {
}
