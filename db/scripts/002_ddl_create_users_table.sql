CREATE TABLE IF NOT EXISTS users
(
    id SERIAL PRIMARY KEY,
    name varchar NOT NULL,
    login varchar UNIQUE NOT NULL,
    password varchar NOT NULL
);