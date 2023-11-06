package io.springbatch.springbatchlecture.part8_itemreader.adapter;

public class CustomService<T> {

    private int cnt = 0;

    public T read(T item) {
        return (T) ("item" + cnt++);
    }
}
