package dev.com.community.community.repository;

import dev.com.community.community.entity.BoardEntity;
import dev.com.community.user.entity.UserEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends JpaRepository<BoardEntity, Long> {
    List<BoardEntity> findBoardEntityByUserEntity(UserEntity userEntity);
}
