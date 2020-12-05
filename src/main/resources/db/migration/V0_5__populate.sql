DROP TABLE IF EXISTS messages CASCADE;

CREATE TABLE messages(
	id SERIAL PRIMARY KEY, 
	text VARCHAR NOT NULL,
	tag VARCHAR,
	user_id INTEGER NOT NULL,
	filename VARCHAR,
	FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);

INSERT INTO messages (text, tag, user_id) VALUES
	('Hello!', 'None', '1'),
	('Salut!', 'None', '2');