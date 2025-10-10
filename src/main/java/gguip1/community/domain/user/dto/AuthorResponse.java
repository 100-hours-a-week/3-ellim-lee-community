package gguip1.community.domain.user.dto;

import lombok.Data;

/**
 * 작성자 정보 응답 DTO입니다. (comment, post 작성자 등)
 * Fields:
 *  nickname - 작성자의 닉네임
 *  imageUrl - 작성자의 프로필 이미지 URL
 */
@Data
public class AuthorResponse {
//    private Integer userId;
    private String nickname;
    private String imageUrl;
}
