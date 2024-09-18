//어떤 repository에 있는 함수를 사용하는 지 연결하는 곳
package com.example.yourssu;

import com.example.yourssu.repository.*;
import com.example.yourssu.service.BoardService;
import com.example.yourssu.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import javax.sql.DataSource;

@Configuration
public class SpringConfig {

    private DataSource dataSource;

    @Autowired
    public SpringConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    @Bean
    public MemberService memberService() {
        return new MemberService(memberRepository());
    }
    @Bean
    public MemberRepository memberRepository() {
        //return new MemoryMemberRepository();
        //return new JdbcMemberRepository(dataSource);
        return new JdbcTemplateMemberRepository(dataSource);
    }
    @Bean
    public BoardService boardService() {
        return new BoardService(boardRepository());
    }
    @Bean
    public BoardRepository boardRepository() {
        return new JdbcTemplateBoardRepository(dataSource);
    }
}
