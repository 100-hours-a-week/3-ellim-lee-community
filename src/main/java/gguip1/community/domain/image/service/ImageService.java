package gguip1.community.domain.image.service;

import gguip1.community.domain.image.dto.ImageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class ImageService {
    private final FileService fileService;
    private final  ImageCompressor imageCompressor;

    public ImageResponse uploadImage(MultipartFile multipartFile) throws IOException {
        MultipartFile compressedImage = imageCompressor.compressImage(multipartFile, 0.7f);

        String imageUrl = fileService.uploadFile(compressedImage);
        ImageResponse response = new ImageResponse();
        response.setImageUrl(imageUrl);
        return response;
    }
}
