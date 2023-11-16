package io.springbatch.springbatchlecture.part11_repeat_errorcontrol.retry.api;

import io.springbatch.springbatchlecture.part11_repeat_errorcontrol.retry.NoRetryException;
import org.springframework.batch.item.ItemProcessor;

public class RetryItemProcessor implements ItemProcessor<String, String> {

    private int cnt = 0;

    @Override
    public String process(String item) throws Exception {
        if(item.equals("2") || item.equals("3")) {
            cnt++;
            throw new NoRetryException("Process failed. cnt:" + cnt);
        }
        System.out.println("cnt = " + cnt);
        return item;
    }
}
