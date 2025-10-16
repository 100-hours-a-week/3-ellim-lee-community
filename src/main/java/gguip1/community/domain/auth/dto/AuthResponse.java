package gguip1.community.domain.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class AuthResponse {
    private Long userId;
    private String email;
    private String profileImageUrl;
    private String nickname;
}
