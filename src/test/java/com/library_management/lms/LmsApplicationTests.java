package com.library_management.lms;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.library_management.lms.controller.BooksController;
import com.library_management.lms.controller.UserController;
import com.library_management.lms.model.Books;
import com.library_management.lms.model.User;
import com.library_management.lms.repository.BookRepository;
import com.library_management.lms.repository.UserRepository;

@ExtendWith(SpringExtension.class)
@WebMvcTest({ UserController.class, BooksController.class })
class LmsApplicationTests {

	@Autowired
	public MockMvc mockMvc;

	@MockBean
	private BookRepository bookRepository;

	@MockBean
	private UserRepository userRepository;

	@WithMockUser("/rajat@gmail.com")
	@Test
	public void getAllUsersTest() throws Exception {
		User user = new User(1000, "Temp Name", "temp@id.com", "password", "USER", -1, -1, -1);
		List<User> allUsers = Arrays.asList(user);
		Mockito.when(userRepository.findAll()).thenReturn(allUsers);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/users")
				.accept(org.springframework.http.MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		String expected = "[{\"id\":1000,\"name\":\"Temp Name\",\"email\":\"temp@id.com\",\"password\":\"password\",\"roles\":\"USER\",\"bookId1\":-1,\"bookId2\":-1,\"bookId3\":-1}]";
		assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
	}

	@WithMockUser("/rajat@gmail.com")
	@Test
	public void createUserTest() throws Exception {
		User user = new User(1000, "Temp Name", "temp@id.com", "password", "USER", -1, -1, -1);
		Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(user);
		String exampleUserJson = "{\"name\":\"Temp Name\",\"email\":\"temp@id.com\",\"password\":\"password\"}";
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/users").accept(MediaType.APPLICATION_JSON)
				.content(exampleUserJson).contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		String expected = "{\"id\":1000,\"name\":\"Temp Name\",\"email\":\"temp@id.com\",\"password\":\"password\",\"roles\":\"USER\",\"bookId1\":-1,\"bookId2\":-1,\"bookId3\":-1}";
		assertEquals(HttpStatus.OK.value(), response.getStatus());
		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
	}

	@WithMockUser("/rajat@gmail.com")
	@Test
	public void updateUserTest() throws Exception {
		User user = new User(1000, "Temp Name New", "temp@id.com", "password", "USER", -1, -1, -1);
		User updatedUser = new User(1000, "Temp Name New", "temp@id.com", "password", "USER", -1, -1, -1);
		Optional<User> userOp = Optional.of(user);
		Mockito.when(userRepository.findById(Mockito.any(Integer.class))).thenReturn(userOp);
		Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(updatedUser);
		String updatedUserJson = "{\"name\":\"Temp Name New\",\"email\":\"temp@id.com\",\"password\":\"password\"}";
		RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/users/1000").accept(MediaType.APPLICATION_JSON)
				.content(updatedUserJson).contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		String expected = "{\"id\":1000,\"name\":\"Temp Name New\",\"email\":\"temp@id.com\",\"password\":\"password\",\"roles\":\"USER\",\"bookId1\":-1,\"bookId2\":-1,\"bookId3\":-1}";
		assertEquals(HttpStatus.OK.value(), response.getStatus());
		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
	}

	@WithMockUser("/rajat@gmail.com")
	@Test
	public void deleteUserTest() throws Exception {
		User user = new User(1000, "Temp Name New", "temp@id.com", "password", "USER", -1, -1, -1);
		Optional<User> userOp = Optional.of(user);
		Mockito.when(userRepository.findById(Mockito.any(Integer.class))).thenReturn(userOp);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/users/1000");
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		assertEquals(HttpStatus.OK.value(), response.getStatus());
	}

	@WithMockUser("/rajat@gmail.com")
	@Test
	public void getAllBooksTest() throws Exception {
		Books book = new Books("Temp Name", "temp category", 10);
		List<Books> allBooks = Arrays.asList(book);
		Mockito.when(bookRepository.findAll()).thenReturn(allBooks);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/books")
				.accept(org.springframework.http.MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		String expected = "[{\"name\":\"Temp Name\",\"category\":\"temp category\",\"qty\":10}]";
		assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
	}

	@WithMockUser("/rajat@gmail.com")
	@Test
	public void createBooksTest() throws Exception {
		Books book = new Books("Temp Name", "temp category", 1);
		Mockito.when(bookRepository.save(Mockito.any(Books.class))).thenReturn(book);
		String exampleUserJson = "{\"name\":\"Temp Name\",\"category\":\"temp category\",\"qty\":1}";
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/books").accept(MediaType.APPLICATION_JSON)
				.content(exampleUserJson).contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		String expected = "{\"name\":\"Temp Name\",\"category\":\"temp category\",\"qty\":1}";
		assertEquals(HttpStatus.OK.value(), response.getStatus());
		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
	}

	@WithMockUser("/rajat@gmail.com")
	@Test
	public void updateBookTest() throws Exception {
		Books book = new Books("Temp Name", "temp category", 1);
		Books updatedBooks = new Books("Temp Name New", "temp category", 11);
		Optional<Books> bookOp = Optional.of(book);
		Mockito.when(bookRepository.findById(Mockito.any(Integer.class))).thenReturn(bookOp);
		Mockito.when(bookRepository.save(Mockito.any(Books.class))).thenReturn(updatedBooks);
		String updatedBookJson = "{\"name\":\"Temp Name New\",\"category\":\"temp category\",\"qty\":11}";
		RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/books/1000").accept(MediaType.APPLICATION_JSON)
				.content(updatedBookJson).contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		String expected = "{\"name\":\"Temp Name New\",\"category\":\"temp category\",\"qty\":11}";
		assertEquals(HttpStatus.OK.value(), response.getStatus());
		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
	}

	@WithMockUser("/rajat@gmail.com")
	@Test
	public void deleteBooksTest() throws Exception {
		Books book = new Books("Temp Name", "temp category", 1);
		Optional<Books> bookOp = Optional.of(book);
		Mockito.when(bookRepository.findById(Mockito.any(Integer.class))).thenReturn(bookOp);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/books/1000");
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		assertEquals(HttpStatus.OK.value(), response.getStatus());
	}

	@WithMockUser("/rajat@gmail.com")
	@Test
	public void issueBookTest() throws Exception {
		Books book = new Books("Temp Name", "temp category", 12);
		Books updatedBooks = new Books("Temp Name", "temp category", 11);
		User user = new User(1000, "Temp Name", "temp@id.com", "password", "USER", -1, -1, -1);
		User updatedUser = new User(1000, "Temp Name", "temp@id.com", "password", "USER", 1000, -1, -1);
		Optional<Books> bookOp = Optional.of(book);
		Optional<User> userOp = Optional.of(user);
		Mockito.when(bookRepository.findById(Mockito.any(Integer.class))).thenReturn(bookOp);
		Mockito.when(bookRepository.save(Mockito.any(Books.class))).thenReturn(updatedBooks);
		Mockito.when(userRepository.findById(Mockito.any(Integer.class))).thenReturn(userOp);
		Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(updatedUser);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/books/issue/1000/1000")
				.accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		String expected = "{\"id\":1000,\"name\":\"Temp Name\",\"email\":\"temp@id.com\",\"password\":\"password\",\"roles\":\"USER\",\"bookId1\":1000,\"bookId2\":-1,\"bookId3\":-1}";
		assertEquals(HttpStatus.OK.value(), response.getStatus());
		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
	}
}
