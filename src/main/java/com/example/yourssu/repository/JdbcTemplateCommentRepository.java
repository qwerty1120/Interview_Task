package com.example.yourssu.repository;

import com.example.yourssu.domain.Board;
import com.example.yourssu.domain.Comment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class JdbcTemplateCommentRepository implements CommentRepository {
    private final JdbcTemplate jdbcTemplate;

    public JdbcTemplateCommentRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Comment save(Comment comment) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("comment").usingGeneratedKeyColumns("id");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("email", comment.getEmail());
        parameters.put("content", comment.getContent());
        parameters.put("boardId", comment.getBoard().getId());
        parameters.put("password", comment.getPassword());
        //parameters.put("password",encryptPassword(comment.getPassword()));
        //parameters.put("password",encryptPassword(comment.getBoard().getPassword()));
        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
        comment.setId(key.longValue());
        return comment;
    }

    @Override
    public List<Comment> findByBoardId(Long boardId) {
        String sql = "SELECT * FROM comment WHERE board_id = ?";
        return jdbcTemplate.query(sql, commentRowMapper(), boardId);
    }

    @Override
    public List<Comment> findAll() {
        String sql = "SELECT * FROM comment";
        return jdbcTemplate.query(sql, commentRowMapper());
    }

    @Override
    public Optional<Comment> findById(Long id) {
        String sql = "SELECT * FROM comment WHERE id = ?";
        List<Comment> result = jdbcTemplate.query(sql, commentRowMapper(), id);
        return result.stream().findAny();
    }

    private RowMapper<Comment> commentRowMapper() {
        return (rs, rowNum) -> {
            Comment comment = new Comment();
            comment.setId(rs.getLong("id"));
            comment.setEmail(rs.getString("email"));
            comment.setPassword(encryptPassword(rs.getString("password")));
            comment.setContent(rs.getString("content"));

            Long boardId = rs.getLong("board_id");
            Board board = new Board();
            board.setId(boardId);
            comment.setBoard(board);
            return comment;
        };
    }
    private String encryptPassword(String password) {
        // 비밀번호 암호화 (예: BCryptPasswordEncoder 사용)
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }

    @Override
    public void clearStore() {

    }
}
