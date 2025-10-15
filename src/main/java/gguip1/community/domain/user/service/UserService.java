package gguip1.community.domain.user.service;

import gguip1.community.domain.auth.entity.Session;
import gguip1.community.domain.image.entity.Image;
import gguip1.community.domain.image.repository.ImageRepository;
import gguip1.community.domain.user.dto.UserCreateRequest;
import gguip1.community.domain.user.dto.UserPasswordUpdateRequest;
import gguip1.community.domain.user.dto.UserResponse;
import gguip1.community.domain.user.dto.UserUpdateRequest;
import gguip1.community.domain.user.entity.User;
import gguip1.community.domain.user.mapper.UserMapper;
import gguip1.community.domain.user.repository.UserRepository;
import gguip1.community.global.exception.ErrorCode;
import gguip1.community.global.exception.ErrorException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final ImageRepository imageRepository;

    // User <-> User 관련 DTO 상호 변환용 Mapper
    private final UserMapper userMapper;

//    @Transactional
    public void createUser(UserCreateRequest request) {
        if (!request.getPassword().equals(request.getPassword2())){
            throw new ErrorException(ErrorCode.PASSWORD_MISMATCH);
        } // 비밀번호 불일치 확인

        String encryptedPassword = BCrypt.hashpw(request.getPassword(), BCrypt.gensalt());

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new ErrorException(ErrorCode.DUPLICATE_EMAIL);
        } // 이메일 중복 확인, DB Index 설정도 필요

        if (userRepository.existsByNickname(request.getNickname())) {
            throw new ErrorException(ErrorCode.DUPLICATE_NICKNAME);
        } // 닉네임 중복 확인, DB Index 설정도 필요

        Image profileImage = null;
        if (request.getProfileImageId() != null){
            profileImage = imageRepository.findById(request.getProfileImageId())
                    .orElse(null);
        } // 프로필 이미지 설정

        userRepository.save(userMapper.userCreateRequestToUser(request, encryptedPassword, profileImage)); // DB에 저장
    }

    public UserResponse getMyInfo(Session session) {
        return getUser(session.getUserId());
    }

    public UserResponse getUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ErrorException(ErrorCode.USER_NOT_FOUND));
        return userMapper.toResponse(user);
    }

    public void updateMyInfo(Session session, UserUpdateRequest request) {
        updateUser(session.getUserId(), request);
    }

    @Transactional
    public void updateUser(Long userId, UserUpdateRequest request){
//        User user = userRepository.findById(userId)
//                .orElseThrow(() -> new ErrorException(ErrorCode.USER_NOT_FOUND));
//
//        if (request.getNickname() != null && !request.getNickname().equals(user.getNickname())) {
//            boolean exists = userRepository.existsByNickname(request.getNickname());
//            if (exists) {
//                throw new ErrorException(ErrorCode.DUPLICATE_EMAIL);
//            }
//            user.changeNickname(request.getNickname());
//        }
//
//        if (request.getProfileImageId() != null){
//            Image profileImage = imageRepository.findById(request.getProfileImageId())
//                    .orElseThrow(() -> new ErrorException(ErrorCode.NOT_FOUND));
//            user.changeProfileImage(profileImage);
//        }
//
//            userRepository.save(user);
    }

    public void updateMyPassword(Session session, UserPasswordUpdateRequest request) {
        updateUserPassword(session.getUserId(), request);
    }

    @Transactional
    public void updateUserPassword(Long userId, UserPasswordUpdateRequest request){
//        if (!request.getNewPassword().equals(request.getNewPassword2())){
//            throw new ErrorException(ErrorCode.PASSWORD_MISMATCH);
//        }
//
//        User user = userRepository.findById(userId)
//                .orElseThrow(() -> new ErrorException(ErrorCode.USER_NOT_FOUND));
//
//        user.changePassword(BCrypt.hashpw(request.getNewPassword(), BCrypt.gensalt()));
//        userRepository.save(user);
    }

    @Transactional
    public void deleteUser(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new ErrorException(ErrorCode.NOT_FOUND);
        }

        userRepository.deleteById(userId);
    }
}
