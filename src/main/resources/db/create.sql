SET MODE PostgreSQL;

CREATE TABLE IF NOT EXISTS hikes (
    idHike INT PRIMARY KEY auto_increment,
    hike VARCHAR,
    description VARCHAR,
    averageRating INTEGER,
    numberOfReviews INTEGER,
    idLocation INTEGER,
    dateCreated VARCHAR
);

CREATE TABLE IF NOT EXISTS locations (
    idLocation INT PRIMARY KEY auto_increment,
    location VARCHAR,
    dateCreated VARCHAR
);

CREATE TABLE IF NOT EXISTS reviews (
    idReview INT PRIMARY KEY auto_increment,
    title VARCHAR,
    review VARCHAR,
    rating INTEGER,
    idHike INTEGER,
    dateCreated VARCHAR
);

CREATE TABLE IF NOT EXISTS comments (
    idComment INT PRIMARY KEY auto_increment,
    comment VARCHAR,
    idReview INTEGER,
    dateCreated VARCHAR
 );