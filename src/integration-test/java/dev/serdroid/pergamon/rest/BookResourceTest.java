package dev.serdroid.pergamon.rest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.List;

import org.apache.http.HttpStatus;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import dev.serdroid.pergamon.model.Book;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

@QuarkusTest
@QuarkusTestResource(PostgresDBContainer.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BookResourceTest {

    @Test
    @Order(0)
    void testGetAllBooks() {
		Response response = given().contentType(ContentType.JSON).when().get("/api/books");
		response.then()
			.statusCode(200)
			.contentType(ContentType.JSON)
			;
		List<Book> allBooks = response.jsonPath().getList("", Book.class);
		assertThat(allBooks.size(), is(3));
		assertThat(allBooks.get(0).title, is("1984"));
		assertThat(allBooks.get(1).title, is("Brave New World"));
		assertThat(allBooks.get(2).author, is("Yevgeny Zamyatin"));
    }
    
    @Test
    @Order(1)
    void testfindBookByIdWhenBookIsFound() {
		Response response = given().contentType(ContentType.JSON).when().get("/api/books/2");
		response.then()
			.statusCode(200)
			.contentType(ContentType.JSON)
			;
		Book found = response.jsonPath().getObject("", Book.class);
		assertThat(found.title, is("Brave New World"));
		assertThat(found.author, is("Aldous Huxley"));
    }
    
    @Test
    @Order(2)
    void testcreateBook() {
    	Book newBook = new Book();
    	newBook.title = "Fahrenheit 451";
    	newBook.author = "Ray Bradbury";
    	newBook.author = "9781451673319";
    	newBook.price = new BigDecimal(15);
		String location = given().contentType(ContentType.JSON).body(newBook).post("/api/books")
		.then()
			.statusCode(HttpStatus.SC_CREATED)
			.extract().header("Location");

		assertTrue(location.contains("/api/books/"));
		int newid = Integer.parseInt( location.substring( location.lastIndexOf('/') + 1 ) );
		assertThat(newid, is(4));
    }
    
}