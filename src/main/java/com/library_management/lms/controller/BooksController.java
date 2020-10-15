package com.library_management.lms.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.library_management.lms.exception.ResourceNotFoundException;
import com.library_management.lms.model.Books;
import com.library_management.lms.model.User;
import com.library_management.lms.repository.BookRepository;
import com.library_management.lms.repository.UserRepository;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/")
public class BooksController {
	@Autowired
	private BookRepository bookRepository;

	@Autowired
	private UserRepository userRepository;

	@GetMapping("/books")
	public List<Books> getAllBooks() {
		return bookRepository.findAll();
	}

	// create book
	@PostMapping("/books")
	public Books createBook(@RequestBody Books book) {
		return bookRepository.save(book);
	}

	// get book by id
	@GetMapping("/books/{id}")
	public ResponseEntity<Books> getBookById(@PathVariable Integer id) {
		Books book = bookRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Book does not exist with id: " + id));
		return ResponseEntity.ok(book);
	}

	// update book
	@PutMapping("/books/{id}")
	public ResponseEntity<Books> updateBook(@PathVariable Integer id, @RequestBody Books bookDetails) {
		Books book = bookRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Book does not exist with id: " + id));

		book.setCategory(bookDetails.getCategory());
		book.setName(bookDetails.getName());
		book.setQty(bookDetails.getQty());

		Books updatedBook = bookRepository.save(book);
		return ResponseEntity.ok(updatedBook);
	}

	// delete book
	@DeleteMapping("/books/{id}")
	public ResponseEntity<Map<String, Boolean>> deleteBook(@PathVariable Integer id) {
		Books book = bookRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Book does not exist with id: " + id));
		bookRepository.delete(book);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return ResponseEntity.ok(response);
	}

	// delete book
	@GetMapping("/books/issue/{userId}/{bookId}")
	public ResponseEntity<User> issueBook(@PathVariable Integer userId, @PathVariable Integer bookId) {
		Books book = bookRepository.findById(bookId)
				.orElseThrow(() -> new ResourceNotFoundException("Book does not exist with id: " + bookId));

		book.setQty(book.getQty() - 1);
		bookRepository.save(book);

		User user = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User does not exist with id: " + userId));
		if (user.getBookId1() == -1) {
			user.setBookId1(bookId);
		} else if (user.getBookId2() == -1) {
			user.setBookId2(bookId);
		} else if (user.getBookId3() == -1) {
			user.setBookId3(bookId);
		}

		User updatedUser = userRepository.save(user);
		return ResponseEntity.ok(updatedUser);
	}

	@GetMapping("/books/return/{userId}/{bookId}")
	public ResponseEntity<User> returnBook(@PathVariable Integer userId, @PathVariable Integer bookId) {
		Books book = bookRepository.findById(bookId)
				.orElseThrow(() -> new ResourceNotFoundException("Book does not exist with id: " + bookId));

		book.setQty(book.getQty() + 1);
		bookRepository.save(book);

		User user = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User does not exist with id: " + userId));
		if (user.getBookId1() == bookId) {
			user.setBookId1(-1);
		} else if (user.getBookId2() == bookId) {
			user.setBookId2(-1);
		} else if (user.getBookId3() == bookId) {
			user.setBookId3(-1);
		}

		User updatedUser = userRepository.save(user);
		return ResponseEntity.ok(updatedUser);
	}
}
