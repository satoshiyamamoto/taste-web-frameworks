-- noinspection SqlResolveForFile

TRUNCATE TABLE todo;
INSERT INTO todo (id, completed, created_at, title, updated_at) VALUES (1, false, now(), 'title', now());