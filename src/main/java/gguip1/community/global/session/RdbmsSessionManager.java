package gguip1.community.global.session;

import gguip1.community.domain.auth.entity.Session;
import gguip1.community.domain.auth.repository.SessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class RdbmsSessionManager implements SessionManager{
    private final SessionRepository sessionRepository;

    @Override
    public UUID createSession(Integer userId) {
        UUID sessionId = UUID.randomUUID();
        LocalDateTime now = LocalDateTime.now();

        Session session = Session.builder()
                .sessionId(sessionId)
                .userId(userId)
                .createdAt(now)
                .expiresAt(now.plusDays(5))
                .build();

        sessionRepository.save(session);
        return sessionId;
    }

    @Override
    public boolean isValid(UUID sessionId) {
        Optional<Session> sessionOpt = sessionRepository.findById(sessionId);

        if (sessionOpt.isEmpty()) {
            return false;
        }

        Session session = sessionOpt.get();

        return !session.isExpired();
    }

    @Override
    public void removeSession(UUID sessionId) {
        sessionRepository.deleteById(sessionId);
    }

    @Override
    public void cleanUpExpiredSessions() {
        // Todo: 만료된 세션을 정리하는 로직 구현
    }
}
