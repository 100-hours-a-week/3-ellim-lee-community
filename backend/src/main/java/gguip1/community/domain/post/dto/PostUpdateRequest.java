package gguip1.community.domain.post.dto;

import java.util.List;

public record PostUpdateRequest(String title, String content, List<Long> imageIds) {
}
