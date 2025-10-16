package gguip1.community.domain.auth.service;

import gguip1.community.domain.auth.dto.AuthRequest;
import gguip1.community.domain.auth.dto.AuthResponse;
import gguip1.community.domain.user.entity.User;
import gguip1.community.domain.user.repository.UserRepository;
import gguip1.community.global.security.CustomUserDetails;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManager authenticationManager;

    public AuthResponse login(AuthRequest authRequest, HttpServletRequest httpServletRequest) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                    authRequest.getEmail(),
                    authRequest.getPassword()
            )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        HttpSession session = httpServletRequest.getSession(true);
        session.setAttribute(
                HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                SecurityContextHolder.getContext()
        );

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        User user = userDetails.user();

        return AuthResponse.builder()
                .email(user.getEmail())
                .nickname(user.getNickname())
                .profileImageUrl(user.getProfileImage() != null ? user.getProfileImage().getUrl() : null)
                .build();
    }

    public void logout(HttpServletRequest request) {
        SecurityContextHolder.clearContext();

        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
    }
}
