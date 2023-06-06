package dev.com.community.user.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateAccessTokenRequestDTO {
    private String refreshToken;
}
