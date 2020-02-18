insert into brands (id, name) values (1, 'Mercedes');
insert into models (brand_id, name) values (1, 'A class');
insert into models (brand_id, name) values (1, 'B class');
insert into models (brand_id, name) values (1, 'C class');
insert into models (brand_id, name) values (1, 'E class');

insert into brands (id, name) values (2, 'Audi');
insert into models (brand_id, name) values (2, 'A 4');
insert into models (brand_id, name) values (2, 'B 5');
insert into models (brand_id, name) values (2, 'A 6');

insert into brands (id, name) values (3, 'Toyota');
insert into models (brand_id, name) values (3, 'Corola');
insert into models (brand_id, name) values (3, 'Camry');
insert into models (brand_id, name) values (3, 'Crown');

insert into brands (id, name) values (4, 'Aston Martin');
insert into models (brand_id, name) values (4, 'DB 5');
insert into models (brand_id, name) values (4, 'DB 9');

insert into users (login, name, surname, password, phone) values ('root', 'admin', 'sur', '123', '+380985055555');

insert into bodies ("type") values ('Седан');
insert into engines (power, "type", volume) values (150, 'DIESEL', 2.0);
insert into cars (brand, is_active, model, price, shiftgear, year, body_id, engine_id, user_id, description, created) values ('Audi', 'true', 'A 4', 10000, 'AUTO', 2017, 1, 1, 1, 'описание автомобиля', CURRENT_TIMESTAMP);
insert into users_cars (user_id, cars_id) values (1, 1);

insert into bodies ("type") values ('Купе');
insert into engines (power, "type", volume) values (120, 'PETROL', 3.1);
insert into cars (brand, is_active, model, price, shiftgear, year, body_id, engine_id, user_id, description, created) values ('Aston Martin', 'true', 'DB 5', 210000, 'MANUAL', 1965, 2, 2, 1, 'James Bond Car', CURRENT_TIMESTAMP);
insert into users_cars (user_id, cars_id) values (1, 2);
insert into images (car_id, images) values (2, 'aston.jpg');
insert into images (car_id, images) values (2, 'aston 2.jpg');

insert into bodies ("type") values ('Седан');
insert into engines (power, "type", volume) values (180, 'GAS_PETROL', 2.2);
insert into cars (brand, is_active, model, price, shiftgear, year, body_id, engine_id, user_id, description, created) values ('Mercedes', 'true', 'C class', 40000, 'AUTO', 2016, 3, 3, 1, 'new mercedes', CURRENT_TIMESTAMP);
insert into users_cars (user_id, cars_id) values (1, 3);
insert into images (car_id, images) values (3, 'merc1.jpg');
insert into images (car_id, images) values (3, 'merc2.jpg');
insert into images (car_id, images) values (3, 'merc3.jpg');

