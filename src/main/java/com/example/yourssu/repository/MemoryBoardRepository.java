package com.example.yourssu.repository;

import com.example.yourssu.domain.Board;
import com.example.yourssu.domain.Member;

import java.util.*;

public class MemoryBoardRepository implements BoardRepository {
    private static Map<Long, Board> store = new HashMap<>();
    private static long sequence = 0L;

    @Override
    public Board save(Board board) {
        board.setId(++sequence);
        store.put(board.getId(), board);
        return board;
    }

    @Override
    public List<Board> findBoards(String email, String password) {
        List<Board> result = new ArrayList<>();
        for (Board board : store.values()) {
            if (board.getEmail().equals(email) && board.getPassword().equals(password)) {
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
    public void clearStore() {
        store.clear();
    }
}
