package io.springbatch.springbatchlecture.part10_itemprocessor;


import lombok.Getter;
import lombok.ToString;


@Getter
@ToString
public class ProcessorInfo {

    private int id;

    public ProcessorInfo(int id) {
        this.id = id;
    }
}

