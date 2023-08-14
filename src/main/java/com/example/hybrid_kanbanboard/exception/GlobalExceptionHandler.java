package com.example.hybrid_kanbanboard.exception;

import com.example.hybrid_kanbanboard.exception.customException.Boardpermissions;
import com.example.hybrid_kanbanboard.status.MsgResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity<MsgResponseDto> illegalArgumentExceptionHandler(IllegalArgumentException ex) {
        MsgResponseDto msgResponseDto = new MsgResponseDto(ex.getMessage(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(
                // HTTP body
                msgResponseDto,
                // HTTP status code
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler({NullPointerException.class})
    public ResponseEntity<MsgResponseDto> nullPointerExceptionHandler(NullPointerException ex) {
        MsgResponseDto restApiException = new MsgResponseDto(ex.getMessage(), HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(
                // HTTP body
                restApiException,
                // HTTP status code
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler({Boardpermissions.class})
    public ResponseEntity<MsgResponseDto> boardPermissionsHandler(Boardpermissions ex) {
        MsgResponseDto msgResponseDto = new MsgResponseDto("보드 작성자만 수정할 수 있습니다.", HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(
                // HTTP body
                msgResponseDto,
                // HTTP status code
                HttpStatus.BAD_REQUEST
        );
    }
}
