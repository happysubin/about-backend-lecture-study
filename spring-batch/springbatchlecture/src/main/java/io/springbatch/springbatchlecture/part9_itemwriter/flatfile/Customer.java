package io.springbatch.springbatchlecture.part9_itemwriter.flatfile;

import lombok.Data;

@Data
public class Customer {
    private int id ;
    private String name;
    private int age;


    public Customer(int id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }
}
