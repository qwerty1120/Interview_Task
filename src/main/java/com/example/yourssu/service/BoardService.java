package com.example.yourssu.service;

import com.example.yourssu.domain.Board;
import com.example.yourssu.domain.Member;
import com.example.yourssu.dto.BoardRequest;
import com.example.yourssu.dto.BoardResponse;
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
public class BoardService {
    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;
    //private final CommentRepository commentRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    public BoardService(BoardRepository boardRepository, MemberRepository memberRepository) {
        this.boardRepository = boardRepository;
        this.memberRepository = memberRepository;
        //this.commentRepository = commentRepository;
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
    public BoardResponse updateBoard(Long id, BoardRequest boardRequest) {
        // 게시물 조회
        Optional<Board> boardOptional = boardRepository.findById(id);
        Board board = boardOptional.orElseThrow(() -> new IllegalStateException("Board not found"));

        // 본인의 게시물인지 확인
        if (!board.getEmail().equals(boardRequest.getEmail()) ||
                !passwordEncoder.matches(boardRequest.getPassword(), board.getPassword())) {
            throw new IllegalStateException("You are not authorized to edit this post");
        }

        // title과 content가 유효한지 확인
        if (boardRequest.getTitle() == null || boardRequest.getTitle().trim().isEmpty() ||
                boardRequest.getContent() == null || boardRequest.getContent().trim().isEmpty()) {
            throw new IllegalArgumentException("Title and content cannot be empty or null");
        }

        // 게시물 수정
        board.setTitle(boardRequest.getTitle());
        board.setContent(boardRequest.getContent());
        boardRepository.save(board);

        // 수정된 게시물 응답 생성
        BoardResponse response = new BoardResponse();
        response.setArticleId(board.getId());
        response.setEmail(board.getEmail());
        response.setTitle(board.getTitle());
        response.setContent(board.getContent());

        return response;
    }

}
