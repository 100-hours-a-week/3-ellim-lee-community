package gguip1.community.domain.image.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/**
 * 이미지 업로드 요청 DTO입니다.
 * Fields:
 *  image - 업로드할 이미지 파일
 */
@Data
public class ImageRequest {
    private MultipartFile image;
}
