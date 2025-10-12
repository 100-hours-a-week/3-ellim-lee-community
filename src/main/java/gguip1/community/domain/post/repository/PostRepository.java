package gguip1.community.domain.post.repository;

import gguip1.community.domain.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    @Query(value = """
        SELECT * FROM posts
        ORDER BY post_id DESC
        LIMIT :limit""", nativeQuery = true)
    List<Post> findFirstPage(@Param("limit") int limit);

    @Query(value = """
        SELECT * FROM posts
        WHERE :lastPostId IS NULL OR post_id < :lastPostId
        ORDER BY post_id DESC
        LIMIT :limit""", nativeQuery = true)
    List<Post> findNextPage(@Param("lastPostId") Long lastPostId, @Param("limit") int limit);
}
