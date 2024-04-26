package com.example.springdatajdbc.service;

import com.example.springdatajdbc.entity.Book;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {
    private final JdbcTemplate jdbcTemplate;

    public List<Book> getAllBooks() {
        String sql = "SELECT * FROM books";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Book.class));

    }

    public Book getBookById(Long id) {
        String sql = "SELECT * FROM books WHERE id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{id}, (rs, rowNum) ->
                new Book(
                        rs.getLong("id"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getInt("publicationYear")
                ));
        } catch (EntityNotFoundException ex) {
            throw new EntityNotFoundException("Book with id " + id + " not found");
        }
    }

    public Book createBook(Book book) {
        String sql = "INSERT INTO books (title, author, publicationYear) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, book.getTitle(), book.getAuthor(), book.getPublicationYear());
        return book;
    }

    public Book updateBook(Long id, Book book) {
        String sql = "UPDATE books SET title = ?, author = ?, publicationYear = ? WHERE id = ?";
        int updatedRows = jdbcTemplate.update(sql, book.getTitle(), book.getAuthor(), book.getPublicationYear(), id);

        if (updatedRows == 0) {
            throw new EntityNotFoundException("Book with id " + id + " not found");
        }
        return book;
    }

    public void deleteBook(Long id) {
        String sql = "DELETE FROM books WHERE id = ?";
        int deletedRows = jdbcTemplate.update(sql, id);

        if (deletedRows == 0) {
            throw new EntityNotFoundException("Book with id " + id + " not found");
        }
    }
}