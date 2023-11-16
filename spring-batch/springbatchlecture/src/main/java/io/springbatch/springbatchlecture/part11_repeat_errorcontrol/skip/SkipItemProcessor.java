package io.springbatch.springbatchlecture.part11_repeat_errorcontrol.skip;

import io.springbatch.springbatchlecture.part11_repeat_errorcontrol.skip.SkippableException;
import org.springframework.batch.item.ItemProcessor;

public class SkipItemProcessor implements ItemProcessor<String, String> {

    private int cnt = 0;

    @Override
    public String process(String item) throws Exception {

        if(item.equals("6") || item.equals("7")) {
            System.out.println("ex ItemProcessor : " + item);
            cnt++;
            throw new SkippableException("Process failed. cnt:" + cnt);
        }

        System.out.println("ItemProcessor : " + item);
        return String.valueOf(Integer.valueOf(item) * -1);
    }
}
