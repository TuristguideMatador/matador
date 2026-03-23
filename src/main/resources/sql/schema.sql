CREATE
DATABASE matadorDB
    DEFAULT CHARACTER SET utf8mb4;

USE
matadorDB;

CREATE TABLE color
(
    color_id INT AUTO_INCREMENT PRIMARY KEY,
    hex      varchar(7) NOT NULL,
    name     varchar(20)
);


CREATE TABLE location
(
    location_id INT AUTO_INCREMENT PRIMARY KEY,
    name        varchar(20) NOT NULL,
    color_id    INT         NOT NULL,
    FOREIGN KEY (color_id) REFERENCES color (color_id)
);

CREATE TABLE tourist_attraction
(
    attraction_id INT AUTO_INCREMENT PRIMARY KEY,
    location_id   INT,
    name          varchar(20),
    description   varchar(100),
    FOREIGN KEY (location_id) REFERENCES location (location_id)
);

create table tag
(
    tag_id   INT AUTO_INCREMENT PRIMARY KEY,
    tag_name VARCHAR(20)
);

create table attraction_tag
(
    attraction_id int,
    tag_id        int,
    FOREIGN KEY (tag_id) REFERENCES tag (tag_id)
    FOREIGN KEY (attraction_id) REFERENCES tourist_attraction (attraction_id)
);

