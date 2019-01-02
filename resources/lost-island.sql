DROP DATABASE IF EXISTS lostisland;
CREATE DATABASE lostisland;
USE lostisland;

CREATE TABLE User (
ID INTEGER NOT NULL AUTO_INCREMENT,
username VARCHAR(20),
password VARCHAR(20),
conected TINYINT(1),
PRIMARY KEY (ID)
);

CREATE TABLE Player (
ID INTEGER NOT NULL AUTO_INCREMENT,
currentHealth INTEGER,
maxHealth INTEGER,
attack INTEGER,
checkPoint INTEGER,
points INTEGER,
enemiesKilled INTEGER,
PRIMARY KEY (ID),
user_id INTEGER NOT NULL UNIQUE,
FOREIGN KEY (user_id) REFERENCES User(ID)
);

CREATE TABLE GameObject (
ID INTEGER NOT NULL AUTO_INCREMENT,
type VARCHAR(20),
name VARCHAR(20),
objectPoints INTEGER,
cost INTEGER,
PRIMARY KEY (ID)
);

CREATE TABLE Enemy (
ID INTEGER NOT NULL AUTO_INCREMENT,
type VARCHAR(20),
life INT,
map INT,
positionX INT,
positionY INT,
PRIMARY KEY (ID),
player_id INTEGER,
FOREIGN KEY (player_id) REFERENCES Player(ID)
);

CREATE TABLE Players_Gameobjects (
player_id INTEGER NOT NULL, foreign key(player_id) references Player(ID),
gameObject_idGameObject INTEGER NOT NULL, foreign key(gameObject_idGameObject) references GameObject(ID)
);

INSERT INTO User (username, password, conected) VALUES ('Sergi', 'Sergi', false);
INSERT INTO User (username, password, conected) VALUES ('Carlos', 'Carlos', false);
INSERT INTO Player (currentHealth, maxHealth, attack, checkPoint, points, enemiesKilled, user_id) VALUES (6, 6, 1, 1, 0, 0, 1);
INSERT INTO Player (currentHealth, maxHealth, attack, checkPoint, points, enemiesKilled, user_id) VALUES (6, 6, 1, 1, 0, 0, 2);
INSERT INTO GameObject(type, name, objectPoints, cost) VALUES ('BoostDamage','espada',1, 10);
INSERT INTO GameObject(type, name, objectPoints, cost) VALUES ('BoostDamage','martillo',2, 20);
INSERT INTO GameObject(type, name, objectPoints, cost) VALUES ('BoostDamage','bomba',3, 30);
INSERT INTO GameObject(type, name, objectPoints, cost) VALUES ('BoostLife','poison',1, 20);
INSERT INTO GameObject(type, name, objectPoints, cost) VALUES ('Food','banana',1, 5);
INSERT INTO Enemy (type, life, map, positionX, positionY, player_id) VALUES ('Monkey',2,1,3,9, 1);
INSERT INTO Enemy (type, life, map, positionX, positionY, player_id) VALUES ('Monkey',2,2,3,9, 1);
INSERT INTO Enemy (type, life, map, positionX, positionY, player_id) VALUES ('Monkey',2,3,3,9, 1);
INSERT INTO Enemy (type, life, map, positionX, positionY, player_id) VALUES ('Boss',10,4,2,8, 1);
INSERT INTO Enemy (type, life, map, positionX, positionY, player_id) VALUES ('Monkey',2,1,3,9, 2);
INSERT INTO Enemy (type, life, map, positionX, positionY, player_id) VALUES ('Monkey',2,2,3,9, 2);
INSERT INTO Enemy (type, life, map, positionX, positionY, player_id) VALUES ('Monkey',2,3,3,9, 2);
INSERT INTO Enemy (type, life, map, positionX, positionY, player_id) VALUES ('Boss',10,4,2,8, 2);
INSERT INTO players_gameobjects (player_id, gameObject_idGameObject) VALUES (1,1);
INSERT INTO players_gameobjects (player_id, gameObject_idGameObject) VALUES (1,4);
INSERT INTO players_gameobjects (player_id, gameObject_idGameObject) VALUES (2,2);
INSERT INTO players_gameobjects (player_id, gameObject_idGameObject) VALUES (2,5);
