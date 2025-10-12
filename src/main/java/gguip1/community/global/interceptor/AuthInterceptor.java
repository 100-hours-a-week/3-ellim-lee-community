package gguip1.community.global.interceptor;

import gguip1.community.domain.auth.entity.Session;
import gguip1.community.global.annotation.RequireAuth;
import gguip1.community.global.exception.ErrorCode;
import gguip1.community.global.exception.ErrorException;
import gguip1.community.global.session.SessionManager;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jdk.swing.interop.SwingInterOpUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AuthInterceptor implements HandlerInterceptor {
    private final SessionManager sessionManager;
    private static final String SESSION_COOKIE_NAME = "sessionId";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod handlerMethod)){
            return true;
        }

        RequireAuth methodAuth = handlerMethod.getMethodAnnotation(RequireAuth.class);

        if (methodAuth == null){
            return true;
        }

        UUID sessionId = getSessionIdFromCookie(request);

        Session session = sessionManager.getValidSession(sessionId)
                .orElseThrow(() -> new ErrorException(ErrorCode.INVALID_CREDENTIALS));

        request.setAttribute("session", session);

        return true;
    }

    public UUID getSessionIdFromCookie(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies){
                if (SESSION_COOKIE_NAME.equals(cookie.getName())){
                    try {
                        return UUID.fromString(cookie.getValue());
                    } catch (IllegalArgumentException e) {
                        return null;
                    }
                }
            }
        }
        return null;
    }
}
