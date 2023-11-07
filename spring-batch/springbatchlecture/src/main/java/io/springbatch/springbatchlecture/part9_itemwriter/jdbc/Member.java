package io.springbatch.springbatchlecture.part9_itemwriter.jdbc;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class Member {

    private Long id;

    private String name;

    private String email;

    private String password;

    private String role;

    private String authProvider;

    private String profileImg;

    private String thumbnailImg;

    private String createdAt;
    private String status;
    private String updatedAt;

}
