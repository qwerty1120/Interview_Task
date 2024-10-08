//어떤 repository에 있는 함수를 사용하는 지 연결하는 곳
package com.example.yourssu;

import com.example.yourssu.controller.GlobalExceptionHandler;
import com.example.yourssu.repository.*;
import com.example.yourssu.service.BoardService;
import com.example.yourssu.service.CommentService;
import com.example.yourssu.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.sql.DataSource;

@Configuration
public class SpringConfig {

    private final DataSource dataSource;

    @Autowired
    public SpringConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Bean
    public MemberService memberService(BCryptPasswordEncoder passwordEncoder) {
        return new MemberService(memberRepository(), boardRepository(), commentRepository(), passwordEncoder);
    }

    @Bean
    public MemberRepository memberRepository() {
        return new JdbcTemplateMemberRepository(dataSource);
    }

    @Bean
    public CommentService commentService() {
        return new CommentService(boardRepository(), commentRepository());
    }

    @Bean
    public CommentRepository commentRepository() {
        return new JdbcTemplateCommentRepository(dataSource);
    }

    @Bean
    public BoardService boardService() {
        return new BoardService(boardRepository(), commentRepository(), memberRepository());
    }

    @Bean
    public BoardRepository boardRepository() {
        return new JdbcTemplateBoardRepository(dataSource);
    }

    @Bean
    public GlobalExceptionHandler globalExceptionHandler() {
        return new GlobalExceptionHandler();
    }

}
