package gguip1.community.domain.post.dto;

import lombok.Data;

/**
 * 게시글 수정 요청 DTO입니다.
 * Fields:
 *  title - 게시글 제목
 *  content - 게시글 내용
 *  imageUrl - 게시글 이미지 URL
 */
@Data
public class PostUpdateRequest {
    private String title;
    private String content;
    private String imageUrl;
}
