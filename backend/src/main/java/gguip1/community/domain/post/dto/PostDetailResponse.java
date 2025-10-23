package gguip1.community.domain.post.dto;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record PostDetailResponse(Long postId, String title, List<String> imageUrls, String content, AuthorDto author,
                                 LocalDateTime createdAt, Integer likeCount, Integer commentCount, Integer viewCount,
                                 Boolean isAuthor, Boolean isLiked) {
}
