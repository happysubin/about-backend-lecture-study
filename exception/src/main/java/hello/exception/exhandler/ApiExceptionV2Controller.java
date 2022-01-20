package hello.exception.exhandler;


import hello.exception.exception.UserException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
public class ApiExceptionV2Controller {
/*
    @ResponseStatus(HttpStatus.BAD_REQUEST) //이 코드가 없으면 상태 코드가 200으로 정상 완료 된다.
    @ExceptionHandler(IllegalArgumentException.class)
    public ErrorResult illegalExHandle(IllegalArgumentException e) {
        log.error("[exceptionHandle] ex", e);
        return new ErrorResult("BAD", e.getMessage());
    }

    @ExceptionHandler//(UserException.class) 이 코드 대신 인자로 받기 가능
    public ResponseEntity<ErrorResult> userExHandler(UserException e){
        log.error("[exceptionHandle] ex", e);
        ErrorResult errorResult=new ErrorResult("USER-EX", e.getMessage());

        return new ResponseEntity<>(errorResult,HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler
    public ErrorResult exHandle(Exception e){ //실수로 놓친 모든 예외들, 공통 예외들 여기서 처리.
        // Exception은 모든 예외의 부모
        log.error("[exceptionHandle] ex", e);
        return new ErrorResult("EX","내부 오류");
    }
*/
    @GetMapping("/api2/members/{id}")
    public MemberDto getMember(@PathVariable("id") String id) {
        if (id.equals("ex")) {
            throw new RuntimeException("잘못된 사용자");
        }
        if (id.equals("bad")) {
            throw new IllegalArgumentException("잘못된 입력 값");
        }
        if (id.equals("user-ex")) {
            throw new UserException("사용자 오류");
        }
        return new MemberDto(id, "hello " + id);
    }

    @Data
    @AllArgsConstructor
    static class MemberDto {
        private String memberId;
        private String name;
    }

}

/**
 * @ExceptionHandler 예외 처리 방법
 * @ExceptionHandler 애노테이션을 선언하고, 해당 컨트롤러에서 처리하고 싶은 예외를 지정해주면 된다.
 * 해당 컨트롤러에서 예외가 발생하면 이 메서드가 호출된다. 참고로 지정한 예외 또는 그 예외의 자식
 * 클래스는 모두 잡을 수 있다
 *
 * 무조건 포함된 컨트롤러에에서만 적용된다!!!!!
 *
 * 당연히 mvc가 아닌 api사용할 때 자주 사용!!!!
 * */