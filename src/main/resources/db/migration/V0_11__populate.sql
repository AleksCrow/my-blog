DELETE from users where username = 'admin';
DELETE from users_roles where user_id = '1';

INSERT INTO users (username, password, active, email, activation_code) VALUES
	('admin', '$2y$07$GO2KkIBf8RHYUoMDOOEe7uYu9AGso1tiIfW9HL1/AZG2bauWZ0osW ', 'true', 'Qwerton00@gmail.com', 'null');
	
INSERT INTO user_roles (user_id, role) VALUES 
	('1', 'USER'),
	('1', 'ADMIN');