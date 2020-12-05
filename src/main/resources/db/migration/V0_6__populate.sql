DROP TABLE IF EXISTS messages CASCADE;

CREATE TABLE messages(
	id SERIAL PRIMARY KEY, 
	text VARCHAR NOT NULL,
	tag VARCHAR NOT NULL,
	user_id INTEGER NOT NULL,
	filename VARCHAR,
	FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);

INSERT INTO messages (text, tag, user_id) VALUES
	('Hello!', 'none', '1'),
	('Salut!', 'none', '2');