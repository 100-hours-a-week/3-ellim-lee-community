package gguip1.community.domain.post.repository;

import gguip1.community.domain.post.entity.PostStat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostStatRepository extends JpaRepository<PostStat, Long> {
}
