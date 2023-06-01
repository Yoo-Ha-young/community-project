package dev.com.community.common.model;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ResponseErrorDTO {
    private String field;
    private String message;

    public static ResponseErrorDTO of(FieldError e){
        return ResponseErrorDTO.builder()
            .field(e.getField())
            .message(e.getDefaultMessage())
            .build();
    }

    public static List<ResponseErrorDTO> of(List<ObjectError> errors){
        List<ResponseErrorDTO> responseErrors = new ArrayList<>();

        if(errors != null) {
            errors.stream().forEach((e) -> {
                responseErrors.add(ResponseErrorDTO.of((FieldError)e));
            });
        }
        return responseErrors;
    }
}
