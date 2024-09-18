package com.example.yourssu.controller;

import com.example.yourssu.dto.BoardRequest;
import com.example.yourssu.dto.BoardResponse;
import com.example.yourssu.dto.MemberRequest;
import com.example.yourssu.dto.MemberResponse;
import com.example.yourssu.service.BoardService;
import com.example.yourssu.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}