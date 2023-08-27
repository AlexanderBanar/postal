--liquibase formatted sql

--changeset Banar Alexander: id-1
INSERT INTO post_office(id, post_office_index, post_office_name, address)
VALUES (1, '123456', 'Post Office 1', 'Post Office 1 Address');

--changeset Banar Alexander: id-2
INSERT INTO post_office(id, post_office_index, post_office_name, address)
VALUES (1, '254689', 'Post Office 2', 'Post Office 2 Address');

--changeset Banar Alexander: id-3
INSERT INTO post_office(id, post_office_index, post_office_name, address)
VALUES (1, '846215', 'Post Office 3', 'Post Office 3 Address');

--changeset Banar Alexander: id-4
INSERT INTO post_office(id, post_office_index, post_office_name, address)
VALUES (1, '942135', 'Post Office 4', 'Post Office 4 Address');

--changeset Banar Alexander: id-5
INSERT INTO post_office(id, post_office_index, post_office_name, address)
VALUES (1, '553247', 'Post Office 5', 'Post Office 5 Address');
