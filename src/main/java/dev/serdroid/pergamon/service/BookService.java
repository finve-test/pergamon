package dev.serdroid.pergamon.service;

import java.util.List;
import java.util.Optional;

import dev.serdroid.pergamon.model.Book;
import dev.serdroid.pergamon.repository.BookRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class BookService {

	@Inject
	BookRepository bookRepository;
	
	@Transactional(Transactional.TxType.SUPPORTS)
	public List<Book> listAll() {
		return bookRepository.listAll();
	}

	@Transactional(Transactional.TxType.SUPPORTS)
	public Optional<Book> findBookById(Long id) {
		return bookRepository.findByIdOptional(id);
	}
}
