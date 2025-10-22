package gguip1.community.domain.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class AuthResponse {
    private final Long userId;
    private final String email;
    private final String profileImageUrl;
    private final String nickname;
}
