package gguip1.community.domain.user.dto;

import lombok.Builder;

@Builder
public record UserResponse(Long userId, String email, String profileImageUrl, String nickname) {
}
