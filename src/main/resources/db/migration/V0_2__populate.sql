INSERT INTO users (username, password, active, email, activation_code) VALUES
	('admin', '1', 'true', 'Qwerton00@gmail.com', 'null');
	
INSERT INTO user_roles (user_id, role) VALUES 
	('1', 'USER'),
	('1', 'ADMIN');