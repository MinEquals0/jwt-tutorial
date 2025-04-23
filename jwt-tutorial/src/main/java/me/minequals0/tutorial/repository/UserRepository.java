package me.minequals0.tutorial.repository;

import me.minequals0.tutorial.entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// User 엔티티에 매핑됨
// JpaRepo를 상속받아 findAll, save등 다양한 메소드를 기본 사용할 수 있게 됨

public interface UserRepository extends JpaRepository<User, Long> {
    // 쿼리가 수행될 때, Lazy조회가 아닌 Eager 조회로 authorities정보를 가져옴
    @EntityGraph(attributePaths = "authorities")
    // username을 기준으로 User정보를 가져올 때 권한 정보도 함께 가져옴
    Optional<User> findOneWithAuthoritiesByUsername(String username);

}
