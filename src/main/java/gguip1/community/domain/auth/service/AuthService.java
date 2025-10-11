package gguip1.community.domain.auth.service;

import gguip1.community.domain.auth.dto.AuthRequest;
import gguip1.community.domain.auth.entity.Session;
import gguip1.community.domain.auth.repository.SessionRepository;
import gguip1.community.domain.user.entity.User;
import gguip1.community.domain.user.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * 인증 서비스 클래스입니다.
 * Handles user login and logout functionalities.
 * Methods:
 *  - login(AuthRequest request): AuthRequest 객체를 받아 사용자를 인증하고 세션을 생성합니다.
 *  - logout(UUID sessionId): 세션 ID를 받아 해당 세션을 삭제합니다.
 */
@Service
public class AuthService {
    private final SessionRepository sessionRepository;
    private final UserRepository userRepository;

    public AuthService(SessionRepository sessionRepository, UserRepository userRepository) {
        this.sessionRepository = sessionRepository;
        this.userRepository = userRepository;
    }

    public UUID login(AuthRequest request){
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID"));

        if (BCrypt.checkpw(request.getPassword(), user.getPassword())){
            UUID sessionId = UUID.randomUUID();
            LocalDateTime now = LocalDateTime.now();

            Session session = new Session(sessionId, user, now.plusDays(5), now.plusDays(5), now);

            sessionRepository.save(session);

            return sessionId;
        } else {
            throw new IllegalArgumentException("Invalid password");
        }
    }

    public void logout(UUID sessionId){
        if (!sessionRepository.existsById(sessionId)) {
            throw new IllegalArgumentException("Session not found");
        }

        sessionRepository.deleteById(sessionId);
    }
}
