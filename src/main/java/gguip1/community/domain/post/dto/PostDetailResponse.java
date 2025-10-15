package gguip1.community.domain.post.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 게시글 상세 응답 DTO입니다.
 * Fields:
 *  title - 게시글 제목
 *  content - 게시글 내용
 *  author - 작성자 정보 (AuthorResponse)
 *  createdAt - 게시글 작성 시간
 *  likeCount - 좋아요 수
 *  commentCount - 댓글 수
 *  viewCount - 조회수
 *  isAuthor - 현재 사용자가 작성자인지 여부
 *  isLiked - 현재 사용자가 좋아요를 눌렀는지 여부
 */
@Data
@AllArgsConstructor
@Builder
public class PostDetailResponse {
//    private Long postId;
    private String title;
    private String content;
    private List<String> imageUrls;
    private AuthorDto author;
    private LocalDateTime createdAt;
    private Integer likeCount;
    private Integer commentCount;
    private Integer viewCount;
    private Boolean isAuthor;
    private Boolean isLiked;
}
