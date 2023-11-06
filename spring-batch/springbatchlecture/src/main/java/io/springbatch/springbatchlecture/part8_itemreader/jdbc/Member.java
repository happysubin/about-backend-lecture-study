package io.springbatch.springbatchlecture.part8_itemreader.jdbc;

import lombok.Data;

@Data
public class Member {

    private Long id;

    private String name;

    private String email;

    private String password;

    private String role;

    private String authProvider;

    private String profileImg;

    private String thumbnailImg;
}
