package gguip1.community.domain.auth;

import gguip1.community.domain.auth.dto.AuthRequest;
import gguip1.community.domain.auth.service.AuthService;
import gguip1.community.domain.user.entity.User;
import gguip1.community.domain.user.mapper.UserMapper;
import gguip1.community.domain.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private AuthService authService;

    @Test
    void loginSuccess(){
        //given
        AuthRequest authRequest = AuthRequest.builder().
                email("test@test.com").
                password("test1234").
                build();

        //when

        /*
         * 현재 Service의 login이 HttpServletRequest를 요구하기 때문에 테스트 불가 수정 필요
         */
//        authService.login(authRequest);

        //then
    }
}
