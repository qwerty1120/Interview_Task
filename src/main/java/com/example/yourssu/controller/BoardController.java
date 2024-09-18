package com.example.yourssu.controller;

import com.example.yourssu.domain.Board;
import com.example.yourssu.domain.Member;
import com.example.yourssu.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

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
}
