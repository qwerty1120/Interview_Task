package com.example.yourssu.controller;

import com.example.yourssu.dto.BoardRequest;
import com.example.yourssu.dto.BoardResponse;
import com.example.yourssu.dto.MemberRequest;
import com.example.yourssu.dto.MemberResponse;
import com.example.yourssu.service.BoardService;
import com.example.yourssu.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/boards")
public class BoardRequestController {

    @Autowired
    private BoardService boardService;

    @PostMapping("/create")
    public ResponseEntity<BoardResponse> registerBoard(@RequestBody BoardRequest userRequest) {
        BoardResponse userResponse = boardService.registerBoardCreate(userRequest);
        return ResponseEntity.ok(userResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BoardResponse> updateBoard(@PathVariable Long id, @RequestBody BoardRequest boardRequest) {
        BoardResponse updatedBoard = boardService.updateBoard(id, boardRequest);
        return ResponseEntity.ok(updatedBoard);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBoard(
            @PathVariable("id") Long boardId,
            @RequestBody BoardRequest boardRequest) {

        // 요청에서 받은 email과 password를 사용
        String email = boardRequest.getEmail();
        String password = boardRequest.getPassword();

        boardService.deleteBoard(boardId, email, password);
        return ResponseEntity.noContent().build();
    }
}