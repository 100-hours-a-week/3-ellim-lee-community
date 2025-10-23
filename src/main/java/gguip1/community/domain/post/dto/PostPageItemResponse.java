package gguip1.community.domain.post.dto;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record PostPageItemResponse(Long postId, List<String> imageUrls, String title, String content, AuthorDto author,
                                   LocalDateTime createdAt, Integer likeCount, Integer commentCount,
                                   Integer viewCount) {
}
