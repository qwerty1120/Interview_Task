package com.example.yourssu.repository;

import com.example.yourssu.domain.Board;
import com.example.yourssu.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository {
    Comment save(Comment comment);
    List<Comment> findByBoardId(Long boardId);
    List<Comment> findAll();
    Optional<Comment> findById(Long id);
    void deleteByBoardId(Long boardId);
    void clearStore();
    void deleteById(Long id);
}
