package com.example.yourssu.service;

import com.example.yourssu.domain.Board;
import com.example.yourssu.domain.Comment;
import com.example.yourssu.dto.CommentRequest;
import com.example.yourssu.dto.CommentResponse;
import com.example.yourssu.repository.BoardRepository;
import com.example.yourssu.repository.CommentRepository;
import com.example.yourssu.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class CommentService {
    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    public CommentService(BoardRepository boardRepository, CommentRepository commentRepository) {
        this.boardRepository = boardRepository;
        this.commentRepository = commentRepository;
    }

    public CommentResponse addComment(Long boardId, CommentRequest request) {
        // 게시글 존재 여부 확인
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("Board not found"));

        if (request.getEmail() == null || request.getPassword() == null) {
            throw new IllegalArgumentException("Email and password must be provided");
        }
//        // 작성자 인증 (이메일, 비밀번호 확인)
//        if (!passwordEncoder.matches(request.getPassword(), board.getPassword())) {
//            throw new IllegalStateException("Invalid credentials.");
//        }

        // 댓글 내용 확인 ("" 또는 null 체크)
        if (request.getContent() == null || request.getContent().trim().isEmpty()) {
            throw new IllegalArgumentException("Comment content cannot be empty or null.");
        }

        // 댓글 생성
        Comment comment = new Comment();
        comment.setBoard(board);
        comment.setEmail(request.getEmail());
        comment.setContent(request.getContent());
        comment.setPassword(passwordEncoder.encode(request.getPassword()));
        Comment savedComment = commentRepository.save(comment);

        // 응답 생성
        CommentResponse response = new CommentResponse();
        response.setCommentId(savedComment.getId());
        response.setEmail(savedComment.getEmail());
        response.setContent(savedComment.getContent());

        return response;
    }
    public CommentResponse updateComment(Long id,CommentRequest commentRequest) {
        // 댓글 조회
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Comment not found"));
        // 댓글 작성자와 요청자의 이메일 비교
        if (!comment.getEmail().equals(commentRequest.getEmail())) {
            throw new IllegalStateException("Not authorized to update this comment");
        }
        // 댓글 내용 검증
        if (commentRequest.getContent() == null || commentRequest.getContent().trim().isEmpty()) {
            throw new IllegalArgumentException("Content cannot be null or empty");
        }
        // 댓글 수정
        comment.setContent(commentRequest.getContent());
        comment.setPassword(passwordEncoder.encode(commentRequest.getPassword()));
        Comment updatedComment = commentRepository.save(comment);

        // Response 생성
        CommentResponse response = new CommentResponse();
        response.setCommentId(updatedComment.getId());
        response.setEmail(updatedComment.getEmail());
        response.setContent(updatedComment.getContent());

        return response;
    }
}