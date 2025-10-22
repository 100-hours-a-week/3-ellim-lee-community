package gguip1.community.domain.post.dto;

import java.util.List;

public record PostRequest(String title, String content, List<Long> imageIds) {
}
