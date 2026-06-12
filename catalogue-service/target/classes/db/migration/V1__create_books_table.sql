CREATE TABLE books (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(200) NOT NULL,
    author VARCHAR(150) NOT NULL,
    publication_date DATE NOT NULL,
    category VARCHAR(100) NOT NULL,
    isbn VARCHAR(20) NOT NULL UNIQUE,
    rating INTEGER NOT NULL CHECK (rating BETWEEN 1 AND 5),
    visible BOOLEAN NOT NULL DEFAULT TRUE,
    stock INTEGER NOT NULL CHECK (stock >= 0),
    price NUMERIC(10, 2) NOT NULL CHECK (price >= 0)
);