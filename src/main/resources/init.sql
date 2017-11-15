-- Create all tables
CREATE TABLE CAR_MODELS (
	id BIGINT AUTO_INCREMENT,
	brand VARCHAR(50) not NULL, 
	model VARCHAR(50) not NULL,
	price_per_day INTEGER not NULL,
	PRIMARY KEY ( id ),
	UNIQUE ( brand, model )
);
CREATE TABLE CARS (
	plates_number VARCHAR(10) not NULL, 
	car_model_id BIGINT, 
	PRIMARY KEY ( plates_number ),
	FOREIGN KEY ( car_model_id ) REFERENCES CAR_MODELS ( id )
	);
CREATE TABLE CLIENTS ( 
	id BIGINT AUTO_INCREMENT, 
	name VARCHAR(50) not NULL, 
	surname VARCHAR(50) not NULL, 
	PRIMARY KEY ( id )
	);
CREATE TABLE RESERVATIONS ( 
	id BIGINT AUTO_INCREMENT, 
	car_plates_number VARCHAR(10) not NULL, 
	client_id BIGINT,
	start_date TIMESTAMP not NULL,
	expiry_date TIMESTAMP not NULL,
	end_date TIMESTAMP not NULL,
	confirmed BOOLEAN not NULL,
	PRIMARY KEY ( id ),
	FOREIGN KEY ( car_plates_number ) REFERENCES CARS( plates_number ),
	FOREIGN KEY ( client_id ) REFERENCES CLIENTS ( id )
	);
	
-- RANDOM DATA FOR CARS
insert into CAR_MODELS (brand, model, price_per_day) values ('Ford', 'Focus', 21);
insert into CAR_MODELS (brand, model, price_per_day) values ('Volkswagen', 'Type 2', 59);
insert into CAR_MODELS (brand, model, price_per_day) values ('Audi', '100', 54);
insert into CAR_MODELS (brand, model, price_per_day) values ('Dodge', 'Dakota', 43);
insert into CAR_MODELS (brand, model, price_per_day) values ('Chevrolet', 'Corvette', 27);
insert into CAR_MODELS (brand, model, price_per_day) values ('GMC', 'Yukon XL 1500', 34);
insert into CAR_MODELS (brand, model, price_per_day) values ('Dodge', 'Charger', 55);
insert into CAR_MODELS (brand, model, price_per_day) values ('Volkswagen', 'Cabriolet', 35);
insert into CAR_MODELS (brand, model, price_per_day) values ('Chevrolet', 'Silverado 1500', 50);
insert into CAR_MODELS (brand, model, price_per_day) values ('Toyota', 'Tundra', 47);
insert into CAR_MODELS (brand, model, price_per_day) values ('Acura', 'RL', 16);
insert into CAR_MODELS (brand, model, price_per_day) values ('Subaru', 'Outback', 42);
insert into CAR_MODELS (brand, model, price_per_day) values ('Chevrolet', 'Lumina', 33);
insert into CAR_MODELS (brand, model, price_per_day) values ('Volkswagen', 'Golf', 28);
insert into CAR_MODELS (brand, model, price_per_day) values ('BMW', 'M5', 18);
insert into CAR_MODELS (brand, model, price_per_day) values ('Ford', 'F250', 30);
insert into CAR_MODELS (brand, model, price_per_day) values ('Lexus', 'RX', 18);
insert into CAR_MODELS (brand, model, price_per_day) values ('Audi', 'S6', 31);
insert into CAR_MODELS (brand, model, price_per_day) values ('Lincoln', 'Town Car', 56);
insert into CAR_MODELS (brand, model, price_per_day) values ('Cadillac', 'Fleetwood', 45);
insert into CAR_MODELS (brand, model, price_per_day) values ('Ford', 'Ranger', 16);
insert into CAR_MODELS (brand, model, price_per_day) values ('Mazda', 'Mazdaspeed6', 58);
insert into CAR_MODELS (brand, model, price_per_day) values ('GMC', '3500 Club Coupe', 16);
insert into CAR_MODELS (brand, model, price_per_day) values ('Mercedes-Benz', 'Sprinter 3500', 16);
insert into CAR_MODELS (brand, model, price_per_day) values ('Ford', 'Contour', 36);
insert into CAR_MODELS (brand, model, price_per_day) values ('Toyota', 'RAV4', 30);
insert into CAR_MODELS (brand, model, price_per_day) values ('Mitsubishi', 'Challenger', 37);
insert into CAR_MODELS (brand, model, price_per_day) values ('BMW', '7 Series', 46);
insert into CAR_MODELS (brand, model, price_per_day) values ('Scion', 'xB', 16);
insert into CAR_MODELS (brand, model, price_per_day) values ('Mazda', 'B-Series', 40);

-- RANDOM DATA FOR CLIENTS
insert into CLIENTS (name, surname) values ('Aron', 'Feak');
insert into CLIENTS (name, surname) values ('Orville', 'Chetter');
insert into CLIENTS (name, surname) values ('Godard', 'Conklin');
insert into CLIENTS (name, surname) values ('Valle', 'Stopforth');
insert into CLIENTS (name, surname) values ('Lilla', 'Simenet');
insert into CLIENTS (name, surname) values ('Dick', 'Kale');
insert into CLIENTS (name, surname) values ('Rica', 'Adamek');
insert into CLIENTS (name, surname) values ('Pierrette', 'Kliemchen');
insert into CLIENTS (name, surname) values ('Alta', 'Kinane');
insert into CLIENTS (name, surname) values ('Terrye', 'Gabbitus');
insert into CLIENTS (name, surname) values ('Aurelia', 'Tossell');
insert into CLIENTS (name, surname) values ('Carri', 'Benazet');
insert into CLIENTS (name, surname) values ('Shelba', 'Ruusa');
insert into CLIENTS (name, surname) values ('Amalea', 'Lammie');
insert into CLIENTS (name, surname) values ('Stacee', 'Cork');
insert into CLIENTS (name, surname) values ('Albrecht', 'Bottoner');
insert into CLIENTS (name, surname) values ('Camella', 'Tritton');
insert into CLIENTS (name, surname) values ('Fonzie', 'Frill');
insert into CLIENTS (name, surname) values ('Blayne', 'Wickerson');
insert into CLIENTS (name, surname) values ('Lewes', 'Flippini');

-- RANDOM DATA FOR CARS
insert into CARS (plates_number, car_model_id) values ('DW91463', 1);
insert into CARS (plates_number, car_model_id) values ('DW93593', 16);
insert into CARS (plates_number, car_model_id) values ('DW77922', 20);
insert into CARS (plates_number, car_model_id) values ('DW45643', 27);
insert into CARS (plates_number, car_model_id) values ('DW56118', 12);
insert into CARS (plates_number, car_model_id) values ('DW81291', 1);
insert into CARS (plates_number, car_model_id) values ('DW47850', 11);
insert into CARS (plates_number, car_model_id) values ('DW60750', 4);
insert into CARS (plates_number, car_model_id) values ('DW15234', 8);
insert into CARS (plates_number, car_model_id) values ('DW88375', 17);
insert into CARS (plates_number, car_model_id) values ('DW47067', 21);
insert into CARS (plates_number, car_model_id) values ('DW25967', 26);
insert into CARS (plates_number, car_model_id) values ('DW57029', 16);
insert into CARS (plates_number, car_model_id) values ('DW27898', 16);
insert into CARS (plates_number, car_model_id) values ('DW93150', 20);
insert into CARS (plates_number, car_model_id) values ('DW55791', 6);
insert into CARS (plates_number, car_model_id) values ('DW24012', 13);
insert into CARS (plates_number, car_model_id) values ('DW81269', 14);
insert into CARS (plates_number, car_model_id) values ('DW31757', 6);
insert into CARS (plates_number, car_model_id) values ('DW36878', 19);
insert into CARS (plates_number, car_model_id) values ('DW99072', 30);
insert into CARS (plates_number, car_model_id) values ('DW41621', 15);
insert into CARS (plates_number, car_model_id) values ('DW29746', 10);
insert into CARS (plates_number, car_model_id) values ('DW55649', 1);
insert into CARS (plates_number, car_model_id) values ('DW89892', 15);
insert into CARS (plates_number, car_model_id) values ('DW73190', 18);
insert into CARS (plates_number, car_model_id) values ('DW18790', 9);
insert into CARS (plates_number, car_model_id) values ('DW62194', 18);
insert into CARS (plates_number, car_model_id) values ('DW31470', 21);
insert into CARS (plates_number, car_model_id) values ('DW33366', 14);
insert into CARS (plates_number, car_model_id) values ('DW72375', 18);
insert into CARS (plates_number, car_model_id) values ('DW20459', 19);
insert into CARS (plates_number, car_model_id) values ('DW14383', 16);
insert into CARS (plates_number, car_model_id) values ('DW24624', 26);
insert into CARS (plates_number, car_model_id) values ('DW38679', 5);
insert into CARS (plates_number, car_model_id) values ('DW60432', 5);
insert into CARS (plates_number, car_model_id) values ('DW55238', 30);
insert into CARS (plates_number, car_model_id) values ('DW99012', 23);
insert into CARS (plates_number, car_model_id) values ('DW31576', 27);
insert into CARS (plates_number, car_model_id) values ('DW19254', 28);
insert into CARS (plates_number, car_model_id) values ('DW75976', 20);
insert into CARS (plates_number, car_model_id) values ('DW90495', 3);
insert into CARS (plates_number, car_model_id) values ('DW23812', 26);
insert into CARS (plates_number, car_model_id) values ('DW75531', 4);
insert into CARS (plates_number, car_model_id) values ('DW80998', 22);
insert into CARS (plates_number, car_model_id) values ('DW37202', 13);
insert into CARS (plates_number, car_model_id) values ('DW86119', 30);
insert into CARS (plates_number, car_model_id) values ('DW42805', 1);
insert into CARS (plates_number, car_model_id) values ('DW90083', 22);
insert into CARS (plates_number, car_model_id) values ('DW94415', 4);

