package gguip1.community.domain.post.dto;

import lombok.Data;

/**
 * 게시글 수정 응답 DTO입니다.
 * Fields:
 *  postId - 수정된 게시글 ID
 */
@Data
public class PostUpdateResponse {
    private Long postId;
}
