package gguip1.community.domain.post.dto;

import lombok.Data;

import java.util.List;

/**
 * 게시글 페이지 응답 DTO입니다.
 * Fields:
 *  posts - 게시글 목록
 *  hasNext - 다음 페이지 존재 여부
 *  lastPostId - 마지막 게시글 ID (다음 페이지 요청 시 사용)
 */
@Data
public class PostPageResponse {
    private List<PostPageItemResponse> posts;
    private boolean hasNext;
    private Long lastPostId;
}
