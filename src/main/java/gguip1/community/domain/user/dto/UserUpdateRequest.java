package gguip1.community.domain.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * 사용자 정보 수정 요청 DTO입니다.
 * Fields:
 *  nickname - 사용자의 닉네임
 *  profileImageUrl - 프로필 이미지 URL
 */
@Data
public class UserUpdateRequest {
    @Email(message = "올바른 이메일 주소 형식을 입력해주세요.")
    @Size(max = 254, message = "이메일은 최대 254자까지 가능합니다.")
    private String nickname;
    @Size(max = 255, message = "프로필 이미지 URL은 최대 255자까지 가능합니다.")
    private Long profileImageId;
}
