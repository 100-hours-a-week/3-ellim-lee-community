package gguip1.community.domain.post.repository;

import gguip1.community.domain.post.entity.PostImage;
import gguip1.community.domain.post.id.PostImageId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostImageRepository extends JpaRepository<PostImage, PostImageId> {
//    List<PostImage> findAllByPostId(Long postId);
    void deleteAllByPost_PostId(Long postId);
}
