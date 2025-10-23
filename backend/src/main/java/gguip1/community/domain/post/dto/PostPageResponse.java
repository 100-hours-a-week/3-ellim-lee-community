package gguip1.community.domain.post.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record PostPageResponse(List<PostPageItemResponse> posts, boolean hasNext, Long lastPostId) {
}
