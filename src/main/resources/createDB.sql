CREATE DATABASE myblog
    WITH 
    OWNER = "user"
    ENCODING = 'UTF8'
    CONNECTION LIMIT = -1;
GRANT ALL ON DATABASE myblog TO "user";