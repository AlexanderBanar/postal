--liquibase formatted sql

--changeset Banar_Alexander:id_1
CREATE TABLE delivery (
    id serial primary key,
    receiver_index varchar(15),
    receiver_address varchar(255),
    receiver_name varchar(255),
    is_received boolean default false,
    delivery_type varchar(10) default 'UNDEFINED'
);

--changeset Banar_Alexander:id_2
CREATE TABLE post_office (
    id serial primary key,
    post_office_index varchar(15),
    post_office_name varchar(255),
    address varchar(255)
);

--changeset Banar_Alexander:id_3
CREATE TABLE gate (
    id serial primary key,
    delivery_id integer,
    post_office_id integer,
    arrival_date timestamp,
    departure_date timestamp
);
