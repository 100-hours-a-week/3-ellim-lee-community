package gguip1.community.domain.user.mapper;

import gguip1.community.domain.image.entity.Image;
import gguip1.community.domain.user.dto.UserCreateRequest;
import gguip1.community.domain.user.dto.UserResponse;
import gguip1.community.domain.user.entity.User;
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

    public User userCreateRequestToUser(UserCreateRequest request, String encryptedPassword, Image profileImage) {
        return User.builder()
                .profileImage(profileImage)
                .email(request.getEmail())
                .password(encryptedPassword)
                .nickname(request.getNickname())
                .build();
    }
}
