package hello.exception.exhandler.advice;


import hello.exception.exception.UserException;
import hello.exception.exhandler.ErrorResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public ErrorResult illegalExHandle(IllegalArgumentException e) {
        log.error("[exceptionHandle] ex", e);

        return new ErrorResult("BAD",e.getMessage());
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResult> userExHandle(UserException e) {
        log.error("[exceptionHandle] ex", e);
        ErrorResult errorResult = new ErrorResult("USER-EX", e.getMessage());
        return new ResponseEntity<>(errorResult, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler
    public ErrorResult exHandle(Exception e){
        log.error("[exceptionHandle] ex", e);
        return new ErrorResult("EX","내부 오류");
    }
}

//주석 처리했는데도 값이 동일하게 나온다.
//즉 여러 컨트롤러에서 발생한 오류를 여기서 다 처리해준다.

/**
 * @ControllerAdvice
 * @ControllerAdvice 는 대상으로 지정한 여러 컨트롤러에 @ExceptionHandler , @InitBinder 기능을
 * 부여해주는 역할을 한다.
 * @ControllerAdvice 에 대상을 지정하지 않으면 모든 컨트롤러에 적용된다. (글로벌 적용)
 * @RestControllerAdvice 는 @ControllerAdvice 와 같고, @ResponseBody 가 추가되어 있다.
 * @Controller , @RestController 의 차이와 같다.
 *
 * 글로벌말고도 대상 컨트롤러에만 지정 가능.
 */
