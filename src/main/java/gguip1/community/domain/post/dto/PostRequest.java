package gguip1.community.domain.post.dto;

import lombok.Data;

import java.util.List;

/**
 * 게시글 생성 및 수정 요청 DTO입니다.
 * Fields:
 *  title - 게시글 제목
 *  content - 게시글 내용
 *  imageIds - 첨부된 이미지 ID 목록
 */
@Data
public class PostRequest {
    private String title;
    private String content;
    private List<Long> imageIds;
}
