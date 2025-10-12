package gguip1.community.domain.post.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * 게시글 댓글 페이지 응답 DTO입니다.
 * Fields:
 *  comments - 댓글 목록
 *  hasNext - 다음 페이지 존재 여부
 *  lastCommentId - 마지막 댓글 ID (다음 페이지 요청 시 사용)
 */
@Data
@AllArgsConstructor
@Builder
public class PostCommentPageResponse {
    private List<PostCommentPageItemResponse> comments;
    private boolean hasNext;
    private Long lastCommentId;
}
