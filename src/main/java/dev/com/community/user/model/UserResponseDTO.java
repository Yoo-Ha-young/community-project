package dev.com.community.user.model;

import dev.com.community.common.BaseEntity;
import dev.com.community.user.entity.UserEntity;
import java.time.LocalDateTime;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.catalina.User;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserResponseDTO {

    private String email;
    private String username;
    private String phone;

}
