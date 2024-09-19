package com.example.yourssu.controller;

import com.example.yourssu.domain.Board;
import com.example.yourssu.domain.Member;
import com.example.yourssu.dto.BoardResponse;
import com.example.yourssu.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class BoardController {
    private BoardService boardService;

    @Autowired
    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @GetMapping("/boards/new")
    public String cearteBoardForm(){
        return "boards/createBoardForm";
    }

    @PostMapping("/boards/new")
    public String create(BoardForm form){
        Board board = new Board();
        board.setEmail(form.getEmail());
        board.setPassword(form.getPassword());
        board.setTitle(form.getTitle());
        board.setContent(form.getContent());
        boardService.posting(board);

        return "redirect:/";
    }

    @GetMapping("/boards")
    public String list(Model model){
        List<Board> boards = boardService.findAllBoards();
        model.addAttribute("boards", boards);
        return "boards/boardList";
    }

    @GetMapping("/boards/{id}")
    public String getBoardById(@PathVariable Long id, Model model) {
        Optional<Board> board = boardService.findById(id);
        if (board.isPresent()) {
            model.addAttribute("board", board.get());  // board 객체를 모델에 추가
            return "boards/boardDetail";  // boardDetails.html로 이동
        } else {
            return "error/404";  // 게시물을 찾지 못했을 때 404 에러 페이지로 이동
        }
    }

}
