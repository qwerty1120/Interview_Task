package com.example.yourssu.repository;

import com.example.yourssu.domain.Member;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class MemoryMemberRepositoryTest {
    MemberRepository repository = new MemoryMemberRepository();
    @AfterEach
    public void afterEach() {
        repository.clearStore();
    }
    @Test
    public void save(){
        Member member = new Member();
        member.setName("spring");
        repository.save(member);
        Member result = repository.findById(member.getId()).get();
       // System.out.println(result == member);
        //Assertions.assertEquals(member, result);
        //Assertions.assertSame(member, result);
        //assertThat(member).isEqualTo(null);
    }

    @Test
    public void findByName(){
        Member member = new Member();
        member.setName("spring");
        repository.save(member);
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member result = repository.findByName("spring").get();
        assertThat(result).isEqualTo(member);
    }
    @Test
    public void findAll(){
        Member member = new Member();
        member.setName("spring");
        repository.save(member);
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        List<Member> members = repository.findAll();
        assertThat(members.size()).isEqualTo(2);
    }
}
