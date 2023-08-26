--liquibase formatted sql

--changeset Banar Alexander: id-1
CREATE TABLE delivery (
    id integer primary key,
    receiver_index varchar(15),
    receiver_address varchar(255),
    receiver_name varchar(255),
    is_received boolean default false,
    delivery_type varchar(10) default 'UNDEFINED'
);