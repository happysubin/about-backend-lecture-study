package io.springbatch.springbatchlecture.part11_repeat_errorcontrol.retry;

public class RetryableException extends RuntimeException {

    public RetryableException() {
        super();
    }

    public RetryableException(String msg) {
        super(msg);
    }
}
