ALTER TABLE users
ADD COLUMN auth_token VARCHAR (100);

UPDATE users SET auth_token = 'auth1' WHERE username = 'admin';
UPDATE users SET auth_token = 'auth2' WHERE username = 'basic_user';