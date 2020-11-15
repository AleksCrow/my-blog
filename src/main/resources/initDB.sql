DROP TABLE IF EXISTS messages CASCADE;

CREATE TABLE messages(
	id SERIAL PRIMARY KEY, 
	text VARCHAR NOT NULL,
	tag VARCHAR NOT NULL
);

INSERT INTO messages (text, tag) VALUES ('Hello, its first message', 'tag');