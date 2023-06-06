//package dev.com.community.common;
//
//import org.springframework.http.ResponseEntity;
//
//public class ResponseResultDTO {
//    public static ResponseEntity<?> fail(String message) {
//        return ResponseEntity.badRequest().body(ResponseMessageDTO.fail(message));
//    }
//
//
//    public static ResponseEntity<?> fail(String message, Object data) {
//        return ResponseEntity.badRequest().body(ResponseMessageDTO.fail(message));
//    }
//
//    public static ResponseEntity<?> success() {
//        return success(null);
//    }
//
//    public static ResponseEntity<?> success(Object data){
//        return ResponseEntity.ok().body(ResponseMessageDTO.success(data));
//    }
//
//    public static ResponseEntity<?> result(ServiceResult result) {
//        if(result.isFail()) {
//            return fail(result.getMessage());
//        }
//        return success();
//    }
//}
