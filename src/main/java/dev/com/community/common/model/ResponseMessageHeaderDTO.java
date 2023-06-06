package dev.com.community.common.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseMessageHeaderDTO {
    private boolean result;
    private String resultCode;
    private String message;
    private int status;
}
