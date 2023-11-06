package io.springbatch.springbatchlecture.part8_itemreader.jpa;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Embedded
    private Auth auth;

    private String role;

    @Enumerated(EnumType.STRING)
    private AuthProvider authProvider;

    private String profileImg;

    private String thumbnailImg;

}
