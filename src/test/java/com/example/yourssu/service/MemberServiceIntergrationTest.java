package com.example.yourssu.service;

import com.example.yourssu.domain.Member;
import com.example.yourssu.repository.MemberRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional //이거 안쓰면 db에 테스트 데이터 계속 남아있음
        //쓸 경우 데이터를 db에 반영하지 않음
class MemberServiceIntergrationTest {
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    MemberService memberService;

    @Test
    void join() {
        Member member = new Member();
        member.setName("test");

        Long saveId = memberService.join(member);
        Member findMember = memberService.findOne(saveId).get();
        assertThat(member.getName()).isEqualTo(findMember.getName());

    }

    @Test
    public void dupMem() {
        Member member = new Member();
        member.setName("test");

        Member member2 = new Member();
        member2.setName("test");
        memberService.join(member);
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));
        //오른쪽을 실행할 때 왼쪽이 터져야함
        assertThat(e.getMessage()).isEqualTo("already exists");
    }

    @Test
    void findMembers() {
    }

    @Test
    void findOne() {
    }
}