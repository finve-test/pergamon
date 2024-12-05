package dev.serdroid.pergamon.rest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;

import dev.serdroid.pergamon.model.Book;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

@QuarkusTest
@QuarkusTestResource(PostgresDBContainer.class)
class BookResourceTest {

    @Test
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
    
}