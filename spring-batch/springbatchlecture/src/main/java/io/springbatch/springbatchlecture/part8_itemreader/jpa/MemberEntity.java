package io.springbatch.springbatchlecture.part8_itemreader.jpa;


import lombok.*;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
@Table(name = "member2")
public class MemberEntity {

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

    @Builder
    public MemberEntity(Long id, String name, Auth auth, String role, AuthProvider authProvider, String profileImg, String thumbnailImg) {
        this.id = id;
        this.name = name;
        this.auth = auth;
        this.role = role;
        this.authProvider = authProvider;
        this.profileImg = profileImg;
        this.thumbnailImg = thumbnailImg;
    }
}
