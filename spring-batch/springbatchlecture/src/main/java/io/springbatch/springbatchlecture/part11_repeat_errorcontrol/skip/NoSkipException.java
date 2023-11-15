package io.springbatch.springbatchlecture.part11_repeat_errorcontrol.skip;

public class NoSkipException extends Exception {

    public NoSkipException() {
        super();
    }

    public NoSkipException(String msg) {
        super(msg);
    }
}
