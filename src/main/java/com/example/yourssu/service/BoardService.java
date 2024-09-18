package com.example.yourssu.service;

import com.example.yourssu.domain.Board;
import com.example.yourssu.domain.Member;
import com.example.yourssu.repository.BoardRepository;
import com.example.yourssu.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BoardService {
    private final BoardRepository boardRepository;

    @Autowired
    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    //게시하기
    public Long posting(Board board) {
        boardRepository.save(board);
        return board.getId();
    }
    //탐색
    public List<Board> findAllBoards() {
        return boardRepository.findAll();
    }
    public List<Board> findBoardsByEmailAndPassword(String email, String password) {
        return boardRepository.findBoards(email, password);
    }
    public List<Board> findBoardByTitle(String title) {
        return boardRepository.findByTitle(title);
    }
    public Optional<Board> findById(Long id) {
        return boardRepository.findById(id);
    }

}
