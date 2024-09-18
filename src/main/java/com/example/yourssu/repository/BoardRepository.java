//BoardRepository functions
package com.example.yourssu.repository;

import com.example.yourssu.domain.Board;

import java.util.List;
import java.util.Optional;

public interface BoardRepository {
    Board save(Board board);
    List<Board> findBoards(String email, String password);
    List<Board> findAll();
    List<Board> findByTitle(String Title);
    void clearStore();
}
