package gguip1.community.domain.user.mapper;

import gguip1.community.domain.auth.dto.AuthResponse;
import gguip1.community.domain.image.entity.Image;
import gguip1.community.domain.user.dto.UserCreateRequest;
import gguip1.community.domain.user.dto.UserResponse;
import gguip1.community.domain.user.entity.User;
import gguip1.community.global.security.CustomUserDetails;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserResponse toResponse(User user) {
        return UserResponse.builder()
                .userId(user.getUserId())
                .email(user.getEmail())
                .profileImageUrl(user.getProfileImage() != null ? user.getProfileImage().getUrl() : null)
                .nickname(user.getNickname())
                .build();
    }

    public User fromUserCreateRequest(UserCreateRequest request, String encryptedPassword, Image profileImage) {
        return User.builder()
                .profileImage(profileImage)
                .email(request.email())
                .password(encryptedPassword)
                .nickname(request.nickname())
                .build();
    }

    public CustomUserDetails toCustomUserDetails(User user) {
        return CustomUserDetails.builder()
                .userId(user.getUserId())
                .email(user.getEmail())
                .password(user.getPassword())
                .status(user.getStatus())
                .build();
    }

    public AuthResponse toAuthResponse(User user) {
        return AuthResponse.builder()
                .userId(user.getUserId())
                .email(user.getEmail())
                .profileImageUrl(user.getProfileImage() != null ? user.getProfileImage().getUrl() : null)
                .nickname(user.getNickname())
                .build();
    }
}
