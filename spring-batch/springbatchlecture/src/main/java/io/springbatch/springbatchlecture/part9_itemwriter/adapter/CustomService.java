package io.springbatch.springbatchlecture.part9_itemwriter.adapter;

public class CustomService<T> {

    public void customWrite(T item){
        System.out.println("item = " + item);
    }
}
