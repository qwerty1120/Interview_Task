//BoardRepository functions
package com.example.yourssu.repository;

import com.example.yourssu.domain.Board;

import java.util.List;
import java.util.Optional;

public interface BoardRepository {
    Board save(Board board);

    List<Board> findBoards(String email);

    List<Board> findAll();

    List<Board> findByTitle(String Title);

    Optional<Board> findById(Long id);

    void deleteById(Long id);

    void clearStore();
}
