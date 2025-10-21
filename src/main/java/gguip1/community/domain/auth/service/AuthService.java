package gguip1.community.domain.auth.service;

import gguip1.community.domain.auth.dto.AuthRequest;
import gguip1.community.domain.auth.dto.AuthResponse;
import gguip1.community.domain.user.entity.User;
import gguip1.community.domain.user.mapper.UserMapper;
import gguip1.community.domain.user.repository.UserRepository;
import gguip1.community.global.exception.ErrorCode;
import gguip1.community.global.exception.ErrorException;
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
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public AuthResponse login(AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                    authRequest.getEmail(),
                    authRequest.getPassword()
            )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        User user = userRepository.findById(userDetails.getUserId())
                .orElseThrow(() -> new ErrorException(ErrorCode.USER_NOT_FOUND));

        return userMapper.toAuthResponse(user);
    }

    public void logout() {
    }
}
