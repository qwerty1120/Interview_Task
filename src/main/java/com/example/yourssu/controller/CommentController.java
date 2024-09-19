package com.example.yourssu.controller;

import com.example.yourssu.dto.CommentRequest;
import com.example.yourssu.dto.CommentResponse;
import com.example.yourssu.service.CommentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/boards")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/{id}/comments")
    public ResponseEntity<CommentResponse> addComment(@PathVariable Long id, @RequestBody CommentRequest commentRequest) {
        CommentResponse response = commentService.addComment(id, commentRequest);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{boardId}/comments/{commentId}")
    public ResponseEntity<CommentResponse> updateComment(
            @PathVariable Long commentId,
            //@PathVariable Long boardId,
            @RequestBody @Valid CommentRequest commentRequest) {

        CommentResponse updatedComment = commentService.updateComment(commentId, commentRequest);
        CommentResponse response = new CommentResponse();
        response.setCommentId(updatedComment.getCommentId());
        response.setEmail(updatedComment.getEmail());
        response.setContent(updatedComment.getContent());

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}/comments/{commentId}")
    public ResponseEntity<Void> deleteComment(
            @PathVariable("id") Long boardId,
            @PathVariable("commentId") Long commentId,
            @RequestBody CommentRequest request) {
        String email = request.getEmail();
        String password = request.getPassword();
        commentService.deleteComment(commentId, email, password);
        return ResponseEntity.noContent().build(); // 204 No Content 응답
    }
}