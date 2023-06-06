package dev.com.community.user.controller;


import dev.com.community.user.model.CreateAccessTokenRequestDTO;
import dev.com.community.user.model.CreateAccessTokenResponseDTO;
import dev.com.community.user.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/token")
public class TokenApiController {

    private final TokenService tokenService;

    @PostMapping
    public ResponseEntity<CreateAccessTokenResponseDTO>
            createNewAccessToken(@RequestBody CreateAccessTokenRequestDTO request){
        String newAccessToken = tokenService.createNewAccessToken(request.getRefreshToken());

        return ResponseEntity.status(HttpStatus.CREATED)
            .body(new CreateAccessTokenResponseDTO(newAccessToken));
    }
}
