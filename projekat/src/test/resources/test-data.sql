insert into client (id,address, city, country, deleted, email, enabled,
                  first_name, last_name, last_password_reset_date, password,
                    password_reset_token, password_reset_token_date, penalties, phone_num,
                    picture, rating, reviews_number, acc_type, username, verified)
                    values(1,'Ulica', 'Novi Sad', 'Serbia', false , 'gage@mail.com', true,
                           'Dragan', 'Mirkovic','2022-05-26 21:22:20.062', 'gageeee', null ,null , 0,
                           '+38465454', 'pictures/user_pictures/2.png',4, 3, 1, 'gageee', true);
-- create table CLIENT (
--                                id int8 not null,
--                                address varchar(255) not null,
--                                city varchar(255) not null,
--                                country varchar(255) not null,
--                                deleted boolean not null,
--                                email varchar(255) not null,
--                                enabled boolean,
--                                first_name varchar(255) not null,
--                                last_name varchar(255) not null,
--                                last_password_reset_date timestamp,
--                                password varchar(255) not null,
--                                password_reset_token varchar(255),
--                                password_reset_token_date timestamp,
--                                penalties int4 not null,
--                                phone_num varchar(255) not null,
--                                picture varchar(255) not null,
--                                rating float8 not null,
--                                reviews_number int4 not null,
--                                type int4 not null,
--                                username varchar(255) not null,
--                                verified boolean not null,
--                                primary key (id)
-- );
-- insert into client
--     (id,address, city, country, deleted, email, enabled, first_name, last_name,
--      last_password_reset_date, password, password_reset_token, password_reset_token_date,
--      penalties, phone_num, picture, rating, reviews_number, type, username, verified)
-- values
--        (1,'Ulica', 'Novi Sad', 'Serbia', 'false' , 'gage@mail.com', 'true', 'Dragan', 'Mirkovic',
--         '2022-05-26 21:22:20.062', 'gageeee', null ,null , '0', '+38465454', 'pictures/user_pictures/2.png',
--         '4.0', '3', '1', 'gageee', 'true');

-- create table role(
--     id int ,
--     name varchar(255)
-- );
