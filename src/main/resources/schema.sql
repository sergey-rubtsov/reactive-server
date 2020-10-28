CREATE TABLE client_payload
(
    id          IDENTITY NOT NULL primary key,
    request_id  varchar,
    data        varchar,
    number      numeric  not null
);

CREATE VIEW metrics AS
SELECT count(id) as total,
       avg(number) as average
FROM client_payload;
