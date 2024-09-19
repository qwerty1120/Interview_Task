package com.example.yourssu.repository;

import com.example.yourssu.domain.Comment;
import com.example.yourssu.domain.Comment;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.*;
import java.util.stream.Collectors;

public class MemoryCommentRepository implements CommentRepository {
    private static Map<Long, Comment> store = new HashMap<>();
    private static long sequence = 0L;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public Comment save(Comment comment) {
        comment.setId(++sequence);
        store.put(comment.getId(), comment);
        return comment;
    }

    @Override
    public List<Comment> findAll() {
        return new ArrayList<>(store.values());
    }
    @Override
    public List<Comment> findByBoardId(Long boardId) {
        List<Comment> result = new ArrayList<>();
        for (Comment comment : store.values()) {
            if (comment.getBoard().getId().equals(boardId)) {
                result.add(comment);
            }
        }
        return result;
    }
    @Override
    public Optional<Comment> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public void deleteByBoardId(Long boardId) {
        List<Long> idsToRemove = store.values().stream()
                .filter(comment -> comment.getBoard().getId().equals(boardId))
                .map(Comment::getId)
                .collect(Collectors.toList());

        for (Long id : idsToRemove) {
            store.remove(id);
        }
    }

    @Override
    public void clearStore() {
        store.clear();
    }

    @Override
    public void deleteById(Long id) {
        store.remove(id);
    }
}
