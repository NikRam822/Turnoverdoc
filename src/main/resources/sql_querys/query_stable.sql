DROP TABLE main.request, main.user, main.contact;
CREATE TABLE main.user(
	id SERIAL PRIMARY KEY,
	login TEXT UNIQUE NOT NULL CHECK(login !=''),
	password TEXT NOT NULL CHECK(password !=''),
	access INTEGER DEFAULT 0
);
CREATE TABLE main.contact(
	id SERIAL PRIMARY KEY,
	phone TEXT NOT NULL,
	messanger TEXT,
	email TEXT NOT NULL
);
CREATE TABLE main.request(
	id SERIAL PRIMARY KEY,
	id_user INTEGER REFERENCES main.user(id) ON DELETE RESTRICT,
	id_contact INTEGER REFERENCES main.contact(id) ON DELETE RESTRICT,
	full_name TEXT NOT NULL CHECK(full_name !=''),
	photo_url TEXT NOT NULL CHECK(photo_url !=''),
	pdf_url TEXT NOT NULL CHECK(pdf_url !=''),
	status INTEGER DEFAULT 0
);