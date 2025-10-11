package gguip1.community.global.session;

import java.util.UUID;

public interface SessionManager {
    /**
     * 세션을 생성합니다.
     * @param userId 사용자 ID
     * @return 생성된 세션 ID
     */
    UUID createSession(Integer userId);
    /**
     * 세션이 유효한지 확인합니다.
     * @param sessionId 세션 ID
     * @return 유효하면 true, 그렇지 않으면 false
     */
    boolean isValid(UUID sessionId);
    /**
     * 세션을 제거합니다.
     * @param sessionId 세션 ID
     */
    void removeSession(UUID sessionId);
    /**
     * 만료된 세션을 정리합니다.
     * 주기적인 작업으로 호출될 수 있습니다. (배치 작업)
     */
    void cleanUpExpiredSessions();
}
