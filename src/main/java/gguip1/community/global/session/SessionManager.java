package gguip1.community.global.session;

import gguip1.community.domain.auth.entity.Session;

import java.util.Optional;
import java.util.UUID;

public interface SessionManager {
    Session createSession(Long userId);
    Optional<Session> getValidSession(UUID sessionId);
    void removeSession(UUID sessionId);
    void cleanUpExpiredSessions();
}
