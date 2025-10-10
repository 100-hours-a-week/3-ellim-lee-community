package gguip1.community.domain.post.dto;

import lombok.Data;

/**
 * 게시글 댓글 작성, 수정 요청 DTO입니다.
 * Fields:
 *  content - 댓글 내용
 */
@Data
public class PostCommentRequest {
    private String content;
}
