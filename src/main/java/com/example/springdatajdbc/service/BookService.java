package com.example.springdatajdbc.service;

import com.example.springdatajdbc.entity.Book;
import com.example.springdatajdbc.repository.BookRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;

    public List<Book> getAllBooks() {
        return (List<Book>) bookRepository.findAll();
    }

    public Book getBookById(Long id) {
        return bookRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(String.valueOf(id)));
    }

    public Book createBook(Book book) {
        return bookRepository.save(book);
    }

    public Book updateBook(Long id, Book book) {
        Book savedBook = bookRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(String.valueOf(id)));
        savedBook.setAuthor(book.getAuthor());
        savedBook.setTitle(book.getTitle());
        savedBook.setPublicationYear(book.getPublicationYear());
        return bookRepository.save(book);
    }

    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }
}