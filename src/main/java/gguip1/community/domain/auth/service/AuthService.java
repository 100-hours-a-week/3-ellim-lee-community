package gguip1.community.domain.auth.service;

import gguip1.community.domain.auth.dto.AuthRequest;
import gguip1.community.domain.auth.entity.Session;
import gguip1.community.domain.auth.repository.SessionRepository;
import gguip1.community.domain.user.entity.User;
import gguip1.community.domain.user.repository.UserRepository;
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
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID"));

        if (BCrypt.checkpw(request.getPassword(), user.getPassword())){
            return sessionManager.createSession(user.getUserId());
        } else {
            throw new IllegalArgumentException("Invalid password");
        }
    }

    public void logout(UUID sessionId){
        sessionManager.removeSession(sessionId);
    }
}
