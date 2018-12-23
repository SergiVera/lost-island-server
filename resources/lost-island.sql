DROP DATABASE IF EXISTS lostisland;
CREATE DATABASE lostisland;
USE lostisland;

CREATE TABLE user (
ID INTEGER NOT NULL AUTO_INCREMENT,
username VARCHAR(20),
password VARCHAR(20),
PRIMARY KEY (ID)
);

CREATE TABLE player (
ID INTEGER NOT NULL AUTO_INCREMENT,
currentHealth INTEGER,
maxHealth INTEGER,
attack INTEGER,
checkPoint INTEGER,
currentObject INTEGER,
points INTEGER,
enemiesKilled INTEGER,
PRIMARY KEY (ID),
user_id INTEGER NOT NULL UNIQUE,
FOREIGN KEY (user_id) REFERENCES user(ID)
);

CREATE TABLE gameObject (
ID INTEGER NOT NULL AUTO_INCREMENT,
type VARCHAR(20),
name VARCHAR(20),
objectPoints INTEGER,
PRIMARY KEY (ID)
);

CREATE TABLE enemy (
ID INTEGER NOT NULL AUTO_INCREMENT,
state VARCHAR(20),
positionX INT,
positionY INT,
PRIMARY KEY (ID)
);

CREATE TABLE players_gameobjects (
player_id INTEGER NOT NULL, foreign key(player_id) references player(ID),
gameObject_idGameObject INTEGER NOT NULL, foreign key(gameObject_idGameObject) references gameObject(ID)
);

CREATE TABLE players_enemies (
player_id INTEGER NOT NULL, foreign key(player_id) references player(ID),
enemy_idEnemy INTEGER NOT NULL, foreign key(enemy_idEnemy) references enemy(ID)
);

INSERT INTO user (username, password) VALUES ('Sergi', 'Sergi');
INSERT INTO player (currentHealth, maxHealth, attack, checkPoint, currentObject, points, enemiesKilled, user_id) VALUES (50, 100, 20, 1, 0, 0, 0, 1);
