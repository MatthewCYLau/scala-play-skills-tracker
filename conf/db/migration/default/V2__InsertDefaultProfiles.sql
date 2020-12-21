CREATE EXTENSION "uuid-ossp";
INSERT INTO profiles
    (profile_id, name, email)
SELECT uuid_generate_v4(), 'Jon', 'jon@doe.com'
WHERE
    NOT EXISTS (
        SELECT name FROM profiles WHERE name = 'Jon'
    );

INSERT INTO profiles
    (profile_id, name, email)
SELECT uuid_generate_v4(), 'Jane', 'jane@doe.com'
WHERE
    NOT EXISTS (
        SELECT name FROM profiles WHERE name = 'Jane'
    );