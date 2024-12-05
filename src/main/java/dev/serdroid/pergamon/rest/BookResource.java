package dev.serdroid.pergamon.rest;

import java.util.List;
import java.util.Optional;

import dev.serdroid.pergamon.model.Book;
import dev.serdroid.pergamon.service.BookService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@ApplicationScoped
@Path("/api/books")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BookResource {

	@Inject
	BookService bookService;
	
	@GET
	public Response getAllBooks() {
		List<Book> books = bookService.listAll();
		return Response.ok(books).build();
	}

	@GET
	@Path("/{id}")
	public Response getBookById(@PathParam("id") Long id) {
		Optional<Book> book = bookService.findBookById(id);
		if ( book.isPresent() ) {
			return Response.ok(book.get()).build();
		}
		else {
			return Response.status(Status.NOT_FOUND).build();
		}
	}
}
