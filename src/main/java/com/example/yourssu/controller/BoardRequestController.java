package com.example.yourssu.controller;

import com.example.yourssu.dto.BoardRequest;
import com.example.yourssu.dto.BoardResponse;
import com.example.yourssu.dto.MemberRequest;
import com.example.yourssu.dto.MemberResponse;
import com.example.yourssu.service.BoardService;
import com.example.yourssu.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/boards")
public class BoardRequestController {

    @Autowired
    private BoardService boardService;

    @PostMapping("/register/create")
    public ResponseEntity<BoardResponse> registerBoard(@RequestBody BoardRequest userRequest) {
        BoardResponse userResponse = boardService.registerBoardCreate(userRequest);
        return ResponseEntity.ok(userResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BoardResponse> updateBoard(@PathVariable Long id, @RequestBody BoardRequest boardRequest) {
        BoardResponse updatedBoard = boardService.updateBoard(id, boardRequest);
        return ResponseEntity.ok(updatedBoard);
    }

//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteBoard(
//            @PathVariable Long id,
//            @RequestBody DeleteRequest request) {
//
//        // 게시글 및 댓글 삭제 로직 호출
//        boardService.deleteBoard(id, request.getEmail(), request.getPassword());
//
//        return ResponseEntity.ok().build();  // 성공적으로 삭제되면 200 OK 반환
//    }
}