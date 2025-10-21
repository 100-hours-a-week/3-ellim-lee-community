package gguip1.community.domain.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@AllArgsConstructor
public class UserCreateRequest {
    @Email(message = "올바른 이메일 주소 형식을 입력해주세요.")
    @Size(max = 255, message = "이메일은 최대 255자까지 가능합니다.")
    private String email;
    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Size(min = 8, max = 20, message = "비밀번호는 8자 이상 20자 이하로 입력해주세요.")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+\\-={}\\[\\]:\";'<>?,./]).{8,20}$",
            message = "비밀번호는 8자 이상, 20자 이하이며, 대문자, 소문자, 숫자, 특수문자를 각각 최소 1개 포함해야 합니다.") // 정규 표현식: 최소 하나의 대문자, 소문자, 숫자, 특수문자 포함
    private String password;
    private String password2;
    @NotBlank(message = "닉네임을 입력해주세요.")
    @Pattern(regexp = "^\\S+$", message = "띄어쓰기를 없애주세요.") // 정규 표현식: 공백 문자가 포함되지 않음
    @Size(max = 30, message = "닉네임은 최대 10자 까지 작성 가능합니다.")
    private String nickname;
    private Long profileImageId;
}
