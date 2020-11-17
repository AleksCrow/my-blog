DROP TABLE IF EXISTS messages CASCADE;

--CREATE TABLE users(
--	id SERIAL PRIMARY KEY,
--	username VARCHAR NOT NULL,
--	password VARCHAR NOT NULL,
--	active BOOLEAN DEFAULT FALSE
--);

CREATE TABLE messages(
	id SERIAL PRIMARY KEY, 
	text VARCHAR NOT NULL,
	tag VARCHAR NOT NULL,
	user_id INTEGER NOT NULL,
	FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);

--CREATE TABLE user_roles(
--	user_id INTEGER NOT NULL,
--  	role    VARCHAR,
--  	CONSTRAINT user_roles_idx UNIQUE (user_id, role),
--  	FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
--);