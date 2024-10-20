CREATE DATABASE prodxpert;

CREATE TYPE userRole AS ENUM ('ADMIN', 'CLIENT');

CREATE TABLE users (
	id BIGINT PRIMARY KEY SERIAL NOT NULL,
	first_name VARCHAR(255) NOT NULL,
	second_name VARCHAR(255) NOT NULL,
	email VARCHAR(255) UNIQUE NOT NULL,
	password VARCHAR(255) NOT NULL,
	type VARCHAR(31) NOT NULL,
	accesslevel INT NOT NULL,
	deliveryaddress VARCHAR(255) NOT NULL,
	paymentmethod VARCHAR(255) NOT NULL
);


CREATE TABLE products (
	id BIGINT PRIMARY KEY SERIAL NOT NULL,
	name VARCHAR(255) NOT NULL,
	description VARCHAR(255) NOT NULL,
	price double precision NOT NULL,
	stock INT NOT NULL,
	picture_path VARCHAR(255) NOT NULL
);

CREATE TYPE orderStatus AS ENUM ('PENDING',
'PROCESSING',
'SHIPPED',
'DELIVERED',
'CANCELED');

CREATE TABLE orders (
	id BIGINT PRIMARY KEY SERIAL NOT NULL,
	dateCommande TIMESTAMP NOT NULL,
	address VARCHAR(255) NOT NULL,
	phone VARCHAR(255) NOT NULL,
	city VARCHAR(255) NOT NULL,
	orderStatus orderStatus NOT NULL,
	user_id BIGINT NOT NULL references users(id),
	total double precision NOT NULL,
);