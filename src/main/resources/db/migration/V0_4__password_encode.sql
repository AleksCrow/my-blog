CREATE EXTENSION IF NOT EXISTS pgcrypto;

UPDATE users SET password = CRYPT(password, gen_salt('bf', 7));