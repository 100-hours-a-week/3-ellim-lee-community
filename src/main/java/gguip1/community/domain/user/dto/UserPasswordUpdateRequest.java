package gguip1.community.domain.user.dto;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;

public record UserPasswordUpdateRequest(
        @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+\\-={}\\[\\]:\";'<>?,./]).{8,20}$",
                message = "비밀번호는 8자 이상, 20자 이하이며, 대문자, 소문자, 숫자, 특수문자를 각각 최소 1개 포함해야 합니다.") String newPassword,
        String newPassword2) {
}
