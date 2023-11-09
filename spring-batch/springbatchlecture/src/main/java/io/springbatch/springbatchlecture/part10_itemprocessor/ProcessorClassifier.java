package io.springbatch.springbatchlecture.part10_itemprocessor;

import lombok.Getter;
import lombok.Setter;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.classify.Classifier;

import java.util.HashMap;
import java.util.Map;

@Setter
@Getter
public class ProcessorClassifier<C, T> implements Classifier<C, T> {

    private Map<Integer, ItemProcessor<ProcessorInfo, ProcessorInfo>> map = new HashMap<>();

    @Override
    public T classify(C classifiable) {
        int id = ((ProcessorInfo) classifiable).getId();
        return (T) map.get(id);
    }
}
