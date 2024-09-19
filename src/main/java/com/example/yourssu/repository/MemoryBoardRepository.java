package com.example.yourssu.repository;

import com.example.yourssu.domain.Board;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.*;

public class MemoryBoardRepository implements BoardRepository {
    private static final Map<Long, Board> store = new HashMap<>();
    private static long sequence = 0L;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public Board save(Board board) {
        board.setId(++sequence);
        store.put(board.getId(), board);
        return board;
    }

    @Override
    public List<Board> findBoards(String email) {
        List<Board> result = new ArrayList<>();
        for (Board board : store.values()) {
            if (board.getEmail().equals(email)) {
                result.add(board);
            }
        }
        return result;
    }


    @Override
    public List<Board> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public List<Board> findByTitle(String title) {
        List<Board> result = new ArrayList<>();
        for (Board board : store.values()) {
            if (board.getTitle().equals(title)) {
                result.add(board);
            }
        }
        return result;
    }

    @Override
    public Optional<Board> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public void deleteById(Long id) {
        store.remove(id);
    }

    @Override
    public void clearStore() {
        //store.clear();
    }
}
