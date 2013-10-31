DROP SCHEMA PUBLIC CASCADE;

CREATE TABLE demo (
	id IDENTITY PRIMARY KEY,
    data VARCHAR(100)
);

CREATE TABLE conference (
    id IDENTITY PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    startDate DATETIME NOT NULL,
    endDate DATETIME NOT NULL
);

CREATE TABLE countries (
   id IDENTITY PRIMARY KEY,
   countrycode VARCHAR(2),
   countryname VARCHAR(42)
);

CREATE TABLE cities (
   id IDENTITY PRIMARY KEY,
   countrycode VARCHAR(2),
   cityname VARCHAR(60)
);

CREATE TABLE users (
   id IDENTITY PRIMARY KEY,
   name VARCHAR(255),
   surname VARCHAR(255),
   email VARCHAR(255) UNIQUE,
   country VARCHAR(100),
   town VARCHAR(100),
   password VARCHAR(64),
   role VARCHAR(23)
);

CREATE INDEX startDate ON conference (startDate);
CREATE INDEX endDate ON conference (endDate);
