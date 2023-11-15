package io.springbatch.springbatchlecture.part11_repeat_errorcontrol.skip;

public class SkippableException extends Exception {

    public SkippableException() {
        super();
    }

    public SkippableException(String msg) {
        super(msg);
    }
}
