package gguip1.community.domain.post.dto;

import gguip1.community.domain.user.dto.AuthorResponse;
import lombok.Data;

/**
 * 댓글 페이지 아이템 응답 DTO입니다.
 * Fields:
 *  commentId - 댓글 ID
 *  content - 댓글 내용
 *  author - 댓글 작성자 정보
 *  createdAt - 댓글 작성 시간
 *  isAuthor - 현재 사용자가 댓글 작성자인지 여부
 */
@Data
public class PostCommentPageItemResponse {
    private Long commentId;
    private String content;
    private AuthorResponse author;
    private String createdAt;
    private Boolean isAuthor;
}
