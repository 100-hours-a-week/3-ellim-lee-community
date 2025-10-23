package gguip1.community.domain.image.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ImageRequest {
    private MultipartFile image;
}
