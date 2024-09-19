package com.example.yourssu.service;

import com.example.yourssu.domain.Board;
import com.example.yourssu.domain.Member;
import com.example.yourssu.dto.MemberRequest;
import com.example.yourssu.dto.MemberResponse;
import com.example.yourssu.repository.BoardRepository;
import com.example.yourssu.repository.CommentRepository;
import com.example.yourssu.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public MemberService(MemberRepository memberRepository, BoardRepository boardRepository,
                         CommentRepository commentRepository, BCryptPasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.boardRepository = boardRepository;
        this.commentRepository = commentRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void deleteMember(String email, String password) {
        Member member = memberRepository.findByEmail(email)

                .orElseThrow(() -> new IllegalArgumentException("Member not found"));

        if (!passwordEncoder.matches(password, member.getPassword())) {
            throw new IllegalStateException("Unauthorized to delete this member");
        }
        List<Board> boards = boardRepository.findBoards(email);
        for (Board board : boards) {
            commentRepository.deleteByBoardId(board.getId());
            boardRepository.deleteById(board.getId());
        }
        memberRepository.delete(member);
    }
//    public MemberResponse deleteMember(String email, String password) {
//        Member member = memberRepository.findByEmail(email)
//
//                .orElseThrow(() -> new IllegalArgumentException("Member not found"));
//
//        if (!passwordEncoder.matches(password, member.getPassword())) {
//            throw new IllegalStateException("Unauthorized to delete this member");
//        }
//        List<Board> boards = boardRepository.findBoards(email);
//        for (Board board : boards) {
//            commentRepository.deleteByBoardId(board.getId());
//            boardRepository.deleteById(board.getId());
//        }
//        memberRepository.delete(member);
//        MemberResponse response = new MemberResponse();
//        response.setUsername(member.getName());
//        response.setEmail(member.getEmail());
//        return response;
//    }

    private void validateDuplicateMember(String email) {
        memberRepository.findByEmail(email)
                .ifPresent(m -> {
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
        validateDuplicateMember(memberRequest.getEmail());
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
