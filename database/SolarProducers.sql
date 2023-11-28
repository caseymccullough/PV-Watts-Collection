-- Database: SolarProducer
--
-- Contains tables for assessing the solar input of independent power producers
-- For various utilities


DROP TABLE IF EXISTS utility;
DROP TABLE IF EXISTS generator;

CREATE TABLE utility (
	utility_id serial,
	name varchar(128) NOT NULL,
	CONSTRAINT PK_utility PRIMARY KEY (utility_id)
);

CREATE TABLE generator (
	generator_id serial,
	utility_id int NOT NULL,
	name varchar(128) NOT NULL,
	street_address1 varchar(128) NOT NULL,
	street_address2 varchar(128),
	city varchar(64) NOT NULL,
	state char(2) NOT NULL,
	zip_code char(5) NOT NULL,
	system_size double NOT NULL,
	module_type int NOT NULL,
	array_type int NOT NULL,
	tilt int NOT NULL,
	CONSTRAINT PK_generator PRIMARY KEY (generator_id),
	CONSTRAINT FK_utility FOREIGN KEY (utility_id)
	    REFERENCES utility_id(utility_id);
);

