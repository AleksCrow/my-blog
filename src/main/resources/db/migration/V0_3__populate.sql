INSERT INTO users (username, password, active, email, activation_code) VALUES
	('user', '1', 'true', 'mail@gmail.com', 'null');
	
INSERT INTO user_roles (user_id, role) VALUES 
	('2', 'USER');