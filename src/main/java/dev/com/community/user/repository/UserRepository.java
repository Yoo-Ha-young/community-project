package dev.com.community.user.repository;

import dev.com.community.user.entity.UserEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByIdAndPassword(Long id, String password);
    Optional<UserEntity> findByUsernameAndPhone(String username, String phone);
    Optional<UserEntity> findByEmail(String email);
}
