package com.example.yourssu.repository;

import com.example.yourssu.domain.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {
    Member save(Member member);
    Optional<Member> findById(Long Id);
    Optional<Member> findByName(String name);
    List<Member> findAll();
    void clearStore();
}
