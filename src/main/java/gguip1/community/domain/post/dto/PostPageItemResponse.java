package gguip1.community.domain.post.dto;

import gguip1.community.domain.user.dto.AuthorResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 게시글 페이지 아이템 응답 DTO입니다.
 * Fields:
 *  postId - 게시글 ID
 *  title - 게시글 제목
 *  content - 게시글 내용
 *  author - 작성자 정보 (AuthorResponse)
 *  createdAt - 게시글 작성 시간
 *  likeCount - 좋아요 수
 *  commentCount - 댓글 수
 *  viewCount - 조회 수
 */
@Data
@AllArgsConstructor
@Builder
public class PostPageItemResponse {
    private Long postId;
    private List<String> imageUrls;
    private String title;
    private String content;
    private AuthorResponse author;
    private LocalDateTime createdAt;
    private Integer likeCount;
    private Integer commentCount;
    private Integer viewCount;
//    private Boolean isAuthor;
//    private Boolean isLiked;
}
