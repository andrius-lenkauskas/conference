DROP SCHEMA PUBLIC CASCADE;

CREATE TABLE conferences (
    id IDENTITY PRIMARY KEY,
    creatorID INTEGER,
    categoryID INTEGER,
    startDate DATETIME NOT NULL,
    endDate DATETIME NOT NULL,
    title VARCHAR(150),
    description VARCHAR(500)
);

CREATE TABLE categories (
   id IDENTITY PRIMARY KEY,
   maincategory VARCHAR(12),
   subcategory VARCHAR(48)
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

CREATE INDEX startDate ON conferences (startDate);
CREATE INDEX endDate ON conferences (endDate);
