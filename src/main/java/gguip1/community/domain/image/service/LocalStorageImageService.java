package gguip1.community.domain.image.service;

import gguip1.community.domain.image.dto.ImageResponse;
import gguip1.community.domain.image.entity.Image;
import gguip1.community.domain.image.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LocalStorageImageService implements ImageService{
    private final ImageRepository imageRepository;

    @Override
    public ImageResponse upload(MultipartFile file) throws IOException {
        String uploadDir = System.getProperty("user.dir") + "/upload/images/"; // 로컬 스토리지 저장
        String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Path path = Paths.get(uploadDir + filename);
        Files.createDirectories(path.getParent());
        file.transferTo(path.toFile());

        Image image = Image.builder()
                .url(uploadDir + filename)
                .createdAt(LocalDateTime.now())
                .build();

        imageRepository.save(image);

        return new ImageResponse() {{
            setImageId(image.getImageId());
            setImageUrl(image.getUrl());
        }};
    }
}
