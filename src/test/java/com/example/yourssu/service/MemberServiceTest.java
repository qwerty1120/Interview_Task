package com.example.yourssu.service;

import com.example.yourssu.domain.Member;
import com.example.yourssu.repository.MemoryMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class MemberServiceTest {
    MemoryMemberRepository memoryMemberRepository;
    MemberService memberService;
    @BeforeEach
    public void beforeEach() {
        memoryMemberRepository = new MemoryMemberRepository();
        memberService = new MemberService(memoryMemberRepository);
    }
    @AfterEach
    public void afterEach() {
        memoryMemberRepository.clearStore();
    }
    @Test
    void join() {
        Member member = new Member();
        member.setName("test");

        Long saveId = memberService.join(member);
        Member findMember = memberService.findOne(saveId).get();
        assertThat(member.getName()).isEqualTo(findMember.getName());

    }
    @Test
    public void dupMem(){
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