package com.example.yourssu.service;

import com.example.yourssu.domain.Board;
import com.example.yourssu.domain.Member;
import com.example.yourssu.dto.BoardResponse;
import com.example.yourssu.dto.MemberRequest;
import com.example.yourssu.dto.MemberResponse;
import com.example.yourssu.repository.MemberRepository;
import com.example.yourssu.repository.MemoryMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }
    //회원가입
    public Long join(Member member) {
        //Optional<Member> result = memberRepository.findByName(member.getName());
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        memberRepository.findByEmail(member.getEmail())
            .ifPresent(m->{
            throw new IllegalStateException("already exists");
        });
    }

    public List<Member> findMembers() {
        return memberRepository.findAll();
    }
    public Optional<Member> findOne(Long id) {
        return memberRepository.findById(id);
    }

    public MemberResponse registerMember(MemberRequest memberRequest) {
        // 도메인 모델로 변환 및 저장 로직
        Member member = new Member();
        member.setEmail(memberRequest.getEmail());
        member.setPassword(memberRequest.getPassword());
        member.setName(memberRequest.getUsername());

        Member savedMember = memberRepository.save(member);
        // 비즈니스 로직 처리 후 UserResponse 생성
        MemberResponse userResponse = new MemberResponse();
        userResponse.setEmail(member.getEmail());
        userResponse.setUsername(member.getName());

        return userResponse;
    }
}
