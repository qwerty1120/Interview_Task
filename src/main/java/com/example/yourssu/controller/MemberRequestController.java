package com.example.yourssu.controller;

import com.example.yourssu.dto.MemberRequest;
import com.example.yourssu.dto.MemberResponse;
import com.example.yourssu.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/members")
public class MemberRequestController {

    @Autowired
    private MemberService memberService;

    @PostMapping("/new")
    public ResponseEntity<MemberResponse> registerMember(@RequestBody MemberRequest userRequest) {
        MemberResponse userResponse = memberService.registerMember(userRequest);
        return ResponseEntity.ok(userResponse);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteMember(
            @RequestBody MemberRequest request) {
        String email = request.getEmail();
        String password = request.getPassword();
        memberService.deleteMember(email, password);
        return ResponseEntity.noContent().build();  // 204 No Content
    }
}