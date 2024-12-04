create database pergamon ENCODING 'UTF-8';
CREATE USER pergamonusr with encrypted password 'Password';
GRANT ALL PRIVILEGES ON DATABASE pergamon TO pergamonusr;
\c pergamon;
GRANT ALL PRIVILEGES ON SCHEMA public TO pergamonusr;

