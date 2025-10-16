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
    private String nickname;
    private Long profileImageId;
}
