package gguip1.community.domain.image.service;

import gguip1.community.domain.image.dto.ImageResponse;
import org.springframework.web.multipart.MultipartFile;

public interface ImageService {
    ImageResponse upload(MultipartFile file) throws Exception;
}
