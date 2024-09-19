package com.example.yourssu.controller;

import com.example.yourssu.domain.Board;
import com.example.yourssu.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@Controller
public class BoardController {
    private final BoardService boardService;

    @Autowired
    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @GetMapping("/boards/new")
    public String createBoardForm() {
        return "boards/createBoardForm";
    }

    @GetMapping("/boards")
    public String list(Model model) {
        List<Board> boards = boardService.findAllBoards();
        model.addAttribute("boards", boards);
        return "boards/boardList";
    }

    @GetMapping("/boards/{id}")
    public String getBoardById(@PathVariable Long id, Model model) {
        Optional<Board> board = boardService.findById(id);
        if (board.isPresent()) {
            model.addAttribute("board", board.get());
            return "boards/boardDetail";
        } else {
            return "error/404";
        }
    }
}
