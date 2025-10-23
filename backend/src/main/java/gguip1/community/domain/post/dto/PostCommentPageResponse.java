package gguip1.community.domain.post.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;

import java.util.List;

@Builder
public record PostCommentPageResponse(List<PostCommentPageItemResponse> comments, boolean hasNext, Long lastCommentId) {
}
