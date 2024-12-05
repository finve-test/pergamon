package dev.serdroid.pergamon.rest;

import java.util.List;
import java.util.Optional;

import dev.serdroid.pergamon.model.Book;
import dev.serdroid.pergamon.service.BookService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import jakarta.ws.rs.core.UriBuilder;
import jakarta.ws.rs.core.UriInfo;

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
	
	@POST
	public Response createBook(Book book, @Context UriInfo uriInfo) {
		Book saved = bookService.persistBook(book);
		UriBuilder uriBuilder = uriInfo.getAbsolutePathBuilder().path(Long.toString(saved.id));
		return Response.created(uriBuilder.build()).build();
	}

	@PUT
	public Response updateBook(Book book) {
		Book updated = bookService.updateBook(book);
		return Response.ok(updated).build();
	}

	@DELETE
	@Path("/{id}")
	public Response deleteBookById(@PathParam("id") Long id) {
		boolean isDeleted = bookService.deleteBookById(id);
		if ( isDeleted ) {
			return Response.status(Status.NO_CONTENT).build();
		}
		else {
			return Response.status(Status.NOT_FOUND).build();
		}
	}

}
