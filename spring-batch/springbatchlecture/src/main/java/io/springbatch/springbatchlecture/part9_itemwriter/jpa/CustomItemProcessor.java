package io.springbatch.springbatchlecture.part9_itemwriter.jpa;

import io.springbatch.springbatchlecture.part8_itemreader.jpa.Auth;
import io.springbatch.springbatchlecture.part8_itemreader.jpa.AuthProvider;
import io.springbatch.springbatchlecture.part8_itemreader.jpa.MemberEntity;
import io.springbatch.springbatchlecture.part9_itemwriter.jdbc.Member;
import org.springframework.batch.item.ItemProcessor;

public class CustomItemProcessor implements ItemProcessor<Member, MemberEntity> {


    @Override
    public MemberEntity process(Member member) throws Exception {
        return MemberEntity.builder()
                //.id(member.getId())
                .name(member.getName())
                .auth(new Auth(member.getEmail(), member.getPassword()))
                .role(member.getRole())
                .authProvider(AuthProvider.valueOf(member.getAuthProvider()))
                .profileImg(member.getProfileImg())
                .thumbnailImg(member.getThumbnailImg())
                .build();
    }
}
