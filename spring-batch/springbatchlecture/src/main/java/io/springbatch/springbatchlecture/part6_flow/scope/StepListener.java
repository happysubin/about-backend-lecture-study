
package io.springbatch.springbatchlecture.part6_flow.scope;

import org.springframework.batch.core.*;
import org.springframework.batch.core.listener.StepExecutionListenerSupport;

public class StepListener implements StepExecutionListener {


    @Override
    public void beforeStep(StepExecution stepExecution) {
        stepExecution.getExecutionContext().putString("name", "user1");
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        return null;
    }
}
