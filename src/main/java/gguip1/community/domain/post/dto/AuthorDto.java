package gguip1.community.domain.post.dto;

import lombok.Builder;

@Builder
public record AuthorDto(String nickname, String profileImageUrl) {
}
