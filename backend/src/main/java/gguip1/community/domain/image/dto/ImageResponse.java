package gguip1.community.domain.image.dto;

import lombok.Data;

/**
 * 이미지 응답 DTO입니다.
 * Fields:
 *  imageId - 이미지 ID
 *  imageUrl - 이미지 URL
 */
@Data
public class ImageResponse {
    private Long imageId;
    private String imageUrl;
}
