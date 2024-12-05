GRANT ALL PRIVILEGES ON DATABASE pergamon TO pergamonusr;
GRANT ALL PRIVILEGES ON SCHEMA public TO pergamonusr;

create sequence book_seq start with 1 increment by 50;
create table book (id bigint not null, title varchar(50), author varchar(50), isbn varchar(50), price numeric(38,2), primary key (id));

insert into book (id, title, author, isbn, price) values(1, '1984', 'George Orwell', '9780451524935', 49.9);
insert into book (id, title, author, isbn, price) values(2, 'Brave New World', 'Aldous Huxley', '9780060850524', 49.9);
insert into book (id, title, author, isbn, price) values(3, 'We', 'Yevgeny Zamyatin', '9780140185850', 29.9);

ALTER SEQUENCE book_seq RESTART WITH 4;

