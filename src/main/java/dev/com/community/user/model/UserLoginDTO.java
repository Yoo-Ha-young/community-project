package dev.com.community.user.model;


import dev.com.community.user.entity.UserEntity;
import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserLoginDTO extends UserEntity {

    @NotBlank(message = "이메일은 필수 입력사항입니다.")
    private String email;

    @NotBlank(message = "비밀번호는 필수 입력사항입니다.")
    private String password;

    public UserEntity toEntity(){
        return UserEntity.builder()
            .email(email)
            .build();
    }

}
