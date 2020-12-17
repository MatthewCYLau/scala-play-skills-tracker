CREATE EXTENSION "uuid-ossp";
INSERT INTO users
    (user_id, name, email)
SELECT uuid_generate_v4(), 'Jon', 'jon@doe.com'
WHERE
    NOT EXISTS (
        SELECT name FROM users WHERE name = 'Jon'
    );

INSERT INTO users
    (user_id, name, email)
SELECT uuid_generate_v4(), 'Jane', 'jane@doe.com'
WHERE
    NOT EXISTS (
        SELECT name FROM users WHERE name = 'Jane'
    );