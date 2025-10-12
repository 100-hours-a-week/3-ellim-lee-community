package gguip1.community.domain.user.service;

import gguip1.community.domain.auth.entity.Session;
import gguip1.community.domain.image.entity.Image;
import gguip1.community.domain.image.repository.ImageRepository;
import gguip1.community.domain.user.dto.UserPasswordUpdateRequest;
import gguip1.community.domain.user.dto.UserRequest;
import gguip1.community.domain.user.dto.UserResponse;
import gguip1.community.domain.user.dto.UserUpdateRequest;
import gguip1.community.domain.user.entity.User;
import gguip1.community.domain.user.repository.UserRepository;
import gguip1.community.global.session.SessionManager;
import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final ImageRepository imageRepository;

    public void createUser(UserRequest request) {
        if (!request.getPassword().equals(request.getPassword2())){
            throw new IllegalArgumentException("Passwords do not match");
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email already in use");
        }

        if (userRepository.existsByNickname(request.getNickname())) {
            throw new IllegalArgumentException("Nickname already in use");
        }

        Image profileImage = null;
        if (request.getProfileImageId() != null) {
            profileImage = imageRepository.findById(request.getProfileImageId())
                    .orElseThrow(() -> new IllegalArgumentException("Profile image not found"));
        }

        User user = User.builder()
                .profileImage(profileImage)
                .email(request.getEmail())
                .password(BCrypt.hashpw(request.getPassword(), BCrypt.gensalt()))
                .nickname(request.getNickname())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        userRepository.save(user);
    }

    public UserResponse getMyInfo(Session session) {
        return getUser(session.getUserId());
    }

    public UserResponse getUser(Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        return new UserResponse(
                user.getEmail(),
                user.getNickname(),
                user.getProfileImage() != null ? user.getProfileImage().getUrl() : null
        );
    }

    public void updateMyInfo(Session session, UserUpdateRequest request) {
        updateUser(session.getUserId(), request);
    }

    public void updateUser(Integer userId, UserUpdateRequest request){
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new IllegalArgumentException("User not found"));

            if (request.getNickname() != null && !request.getNickname().equals(user.getNickname())){
                user.changeNickname(request.getNickname());
            }

            if (request.getProfileImageId() != null){
                Image profileImage = imageRepository.findById(request.getProfileImageId())
                        .orElseThrow(() -> new IllegalArgumentException("Profile image not found"));
                user.changeProfileImage(profileImage);
            }

            userRepository.save(user);
    }

    public void updateMyPassword(Session session, UserPasswordUpdateRequest request) {
        updateUserPassword(session.getUserId(), request);
    }

    public void updateUserPassword(Integer userId, UserPasswordUpdateRequest request){
        if (!request.getNewPassword().equals(request.getNewPassword2())){
            throw new IllegalArgumentException("Passwords do not match");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        user.changePassword(BCrypt.hashpw(request.getNewPassword(), BCrypt.gensalt()));
        userRepository.save(user);
    }

    public void deleteUser(Integer userId) {
        if (!userRepository.existsById(userId)) {
            throw new IllegalArgumentException("User not found");
        }

        userRepository.deleteById(userId);
    }
}
