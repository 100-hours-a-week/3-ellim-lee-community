package gguip1.community.global.security;

import gguip1.community.domain.user.repository.UserRepository;
import gguip1.community.global.exception.ErrorCode;
import gguip1.community.global.exception.ErrorException;
import lombok.RequiredArgsConstructor;
import gguip1.community.domain.user.entity.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new ErrorException(ErrorCode.USER_NOT_FOUND));
        return new CustomUserDetails(user.getUserId(), user.getEmail(), user.getPassword(), user.getStatus());
    }
}
