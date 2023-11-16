package io.springbatch.springbatchlecture.part11_repeat_errorcontrol.retry;

public class NoRetryException extends RuntimeException {

    public NoRetryException() {
        super();
    }
    public NoRetryException(String msg) {
        super(msg);
    }
}
