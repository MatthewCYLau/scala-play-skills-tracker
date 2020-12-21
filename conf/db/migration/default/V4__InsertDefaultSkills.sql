INSERT INTO skills
    (skill_id, name)
SELECT uuid_generate_v4(), 'Python'
WHERE
    NOT EXISTS (
        SELECT name FROM profiles WHERE name = 'Python'
    );

INSERT INTO skills
    (skill_id, name)
SELECT uuid_generate_v4(), 'Java'
WHERE
    NOT EXISTS (
        SELECT name FROM profiles WHERE name = 'Java'
    );