package dev.serdroid.pergamon.model;

import java.math.BigDecimal;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Book extends PanacheEntityBase {
	@Id
	@GeneratedValue
	public Long id;
	public String title;
	public String author;
	public String isbn;
	public BigDecimal price;
}
