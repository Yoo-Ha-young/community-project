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
public class ResponseMessageDTO {

    private ResponseMessageHeaderDTO header;
    private Object data;

    public static ResponseMessageDTO fail(String message){

        return ResponseMessageDTO.builder()
            .header(ResponseMessageHeaderDTO.builder()
                .result(false)
                .resultCode("")
                .message(message)
                .status(HttpStatus.BAD_REQUEST.value())
                .build())
            .data(null)
            .build();
    }


    public static ResponseMessageDTO success(Object data){
        return ResponseMessageDTO.builder()
            .header(ResponseMessageHeaderDTO.builder()
                .result(true)
                .resultCode("")
                .message("")
                .status(HttpStatus.OK.value())
                .build())
            .build();
    }
}
