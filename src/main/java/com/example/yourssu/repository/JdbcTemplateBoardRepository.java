package com.example.yourssu.repository;

import com.example.yourssu.domain.Board;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class JdbcTemplateBoardRepository implements BoardRepository {
    private final JdbcTemplate jdbcTemplate;

    public JdbcTemplateBoardRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Board save(Board board) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("board").usingGeneratedKeyColumns("id");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("email", board.getEmail());
        parameters.put("title", board.getTitle());
        parameters.put("content", board.getContent());
        //parameters.put("password", member.getPassword());
        parameters.put("password", encryptPassword(board.getPassword()));
        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
        board.setId(key.longValue());
        return board;
    }

    private String encryptPassword(String password) {
        // 비밀번호 암호화 (예: BCryptPasswordEncoder 사용)
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }

    @Override
    public List<Board> findBoards(String email) {
        String sql = "SELECT id, email, password, content, title FROM board WHERE email = ?";
        return jdbcTemplate.query(sql, new Object[]{email}, boardRowMapper());
    }

    private RowMapper<Board> boardRowMapper() {
        return (rs, rowNum) -> {
            Board board = new Board();
            board.setId(rs.getLong("id"));
            board.setEmail(rs.getString("email"));
            board.setPassword(rs.getString("password"));
            board.setContent(rs.getString("content"));
            board.setTitle(rs.getString("title"));
            return board;
        };
    }

    @Override
    public List<Board> findAll() {
        return jdbcTemplate.query("select * from board", boardRowMapper());
    }

    @Override
    public List<Board> findByTitle(String Title) {
        String sql = "SELECT id, email, password, content, title FROM board WHERE title = ?";
        return jdbcTemplate.query(sql, new Object[]{Title}, boardRowMapper());
    }

    @Override
    public Optional<Board> findById(Long id) {
        String sql = "SELECT * FROM board WHERE id = ?";

        // RowMapper를 사용하여 결과를 Board 객체로 변환
        return jdbcTemplate.query(sql, new Object[]{id}, boardRowMapper())
                .stream()
                .findFirst();  // 결과 리스트에서 첫 번째 요소를 Optional로 변환
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM board WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public void clearStore() {

    }
}