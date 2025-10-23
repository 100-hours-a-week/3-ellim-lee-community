package gguip1.community.domain.user.repository;

import gguip1.community.domain.user.entity.User;
import org.hibernate.annotations.SQLRestriction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@SQLRestriction("status = 0")
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
    boolean existsByNickname(String nickname);
}
