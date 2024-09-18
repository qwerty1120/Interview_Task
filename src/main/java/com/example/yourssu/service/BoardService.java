package com.example.yourssu.service;

import com.example.yourssu.domain.Board;
import com.example.yourssu.domain.Member;
import com.example.yourssu.dto.BoardRequest;
import com.example.yourssu.dto.BoardResponse;
import com.example.yourssu.dto.MemberRequest;
import com.example.yourssu.dto.MemberResponse;
import com.example.yourssu.repository.BoardRepository;
import com.example.yourssu.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BoardService {
    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    public BoardService(BoardRepository boardRepository, MemberRepository memberRepository) {
        this.boardRepository = boardRepository;
        this.memberRepository = memberRepository;
    }

    //게시하기
    public Long posting(Board board) {
        validateDuplicateBoard(board);
        validateDuplicateMemberEmail(board);
        boardRepository.save(board);
        return board.getId();
    }
    public void validateDuplicateMemberEmail(Board board) {
        boolean emailExists = memberRepository.findByEmail(board.getEmail()).isPresent();
        if (!emailExists) {
            throw new IllegalStateException("No member with email: " + board.getEmail());
        }
    }
    private void validateDuplicateBoard(Board board) {
        List<Board> boards = boardRepository.findBoards(board.getEmail());
        boolean isDuplicate = boards.stream()
                .anyMatch(existingBoard -> existingBoard.getTitle().equals(board.getTitle())); // 제목이 같은지 확인

        // 중복된 게시물이 있으면 예외 던지기
        if (isDuplicate) {
            throw new IllegalStateException("A board with the same title already exists.");
        }
    }

    //탐색
    public List<Board> findAllBoards() {
        return boardRepository.findAll();
    }
    public List<Board> findBoardsByEmailAndPassword(String email, String password) {
        return boardRepository.findBoards(email);
    }
    public List<Board> findBoardByTitle(String title) {
        return boardRepository.findByTitle(title);
    }
    public Optional<Board> findById(Long id) {
        return boardRepository.findById(id);
    }
    public BoardResponse registerBoardCreate(BoardRequest boardRequest) {
        // 도메인 모델로 변환 및 저장 로직
        Board board = new Board();
        board.setEmail(boardRequest.getEmail());
        board.setPassword(boardRequest.getPassword());
        board.setTitle(boardRequest.getTitle());
        board.setContent(boardRequest.getContent());

        Board savedBoard = boardRepository.save(board); // DB에 저장 후 반환된 Board 객체

        // 저장 후 자동 생성된 ID 사용
        BoardResponse boardResponse = new BoardResponse();
        boardResponse.setArticleId(savedBoard.getId()); // 저장 후 생성된 ID
        boardResponse.setEmail(savedBoard.getEmail());
        boardResponse.setTitle(savedBoard.getTitle());
        boardResponse.setContent(savedBoard.getContent());

        return boardResponse;
    }

}
