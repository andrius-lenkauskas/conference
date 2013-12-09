DROP SCHEMA PUBLIC CASCADE;

CREATE TABLE main_categories (
   mainCategoryId IDENTITY PRIMARY KEY,
   mainCategoryName VARCHAR(12)
);

CREATE TABLE sub_categories (
   subCategoryId IDENTITY PRIMARY KEY,
   mainCategoryId INTEGER,
   subCategoryName VARCHAR(48),
   FOREIGN KEY (mainCategoryId) REFERENCES main_categories(mainCategoryId)
);

CREATE TABLE countries (
   countryId IDENTITY PRIMARY KEY,
   countryName VARCHAR(42)
);

CREATE TABLE cities (
   cityId IDENTITY PRIMARY KEY,
   countryId INTEGER,
   cityName VARCHAR(60),
   FOREIGN KEY (countryId) REFERENCES countries(countryId)
);

CREATE TABLE users (
   userId IDENTITY PRIMARY KEY,
   userName VARCHAR(255),
   userSurname VARCHAR(255),
   userEmail VARCHAR(255) UNIQUE,
   countryId INTEGER,
   cityId INTEGER,
   userPassword VARCHAR(64),
   userRole VARCHAR(23),
   FOREIGN KEY (countryId) REFERENCES countries(countryId),
   FOREIGN KEY (cityId) REFERENCES cities(cityId)
);

CREATE TABLE conferences (
    conferenceId IDENTITY PRIMARY KEY,
    creatorId INTEGER,
    categoryId INTEGER,
    startDate DATETIME NOT NULL,
    endDate DATETIME NOT NULL,
    location VARCHAR(255),
    title VARCHAR(150),
    description VARCHAR(500),
    FOREIGN KEY (creatorId) REFERENCES users(userId),
    FOREIGN KEY (categoryId) REFERENCES sub_categories(subCategoryId)
);

CREATE TABLE attendees (
   conferenceId INTEGER,
   attendeeId INTEGER,
   FOREIGN KEY (conferenceId) REFERENCES conferences(conferenceId),
   FOREIGN KEY (attendeeId) REFERENCES users(userId)
);

CREATE INDEX startDate ON conferences (startDate);
CREATE INDEX endDate ON conferences (endDate);
