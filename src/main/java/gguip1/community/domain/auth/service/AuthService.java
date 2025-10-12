package gguip1.community.domain.auth.service;

import gguip1.community.domain.auth.dto.AuthRequest;
import gguip1.community.domain.user.entity.User;
import gguip1.community.domain.user.repository.UserRepository;
import gguip1.community.global.exception.ErrorCode;
import gguip1.community.global.exception.ErrorException;
import gguip1.community.global.session.SessionManager;
import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final SessionManager sessionManager;
    private final UserRepository userRepository;

    public UUID login(AuthRequest request){
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ErrorException(ErrorCode.USER_NOT_FOUND));

        if (BCrypt.checkpw(request.getPassword(), user.getPassword())){
            return sessionManager.createSession(user.getUserId()).getSessionId();
        } else {
            throw new ErrorException(ErrorCode.PASSWORD_MISMATCH);
        }
    }

    public void logout(UUID sessionId){
        sessionManager.removeSession(sessionId);
    }
}
