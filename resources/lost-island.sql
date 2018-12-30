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
PRIMARY KEY (ID)
);

CREATE TABLE Enemy (
ID INTEGER NOT NULL AUTO_INCREMENT,
type VARCHAR(20),
life INT,
positionX INT,
positionY INT,
PRIMARY KEY (ID)
);

CREATE TABLE Players_Gameobjects (
player_id INTEGER NOT NULL, foreign key(player_id) references Player(ID),
gameObject_idGameObject INTEGER NOT NULL, foreign key(gameObject_idGameObject) references GameObject(ID)
);

CREATE TABLE Players_Enemies (
player_id INTEGER NOT NULL, foreign key(player_id) references Player(ID),
enemy_idEnemy INTEGER NOT NULL, foreign key(enemy_idEnemy) references Enemy(ID)
);

INSERT INTO User (username, password, conected) VALUES ('Sergi', 'Sergi', false);
INSERT INTO User (username, password, conected) VALUES ('Carlos', 'Carlos', false);
INSERT INTO Player (currentHealth, maxHealth, attack, checkPoint, points, enemiesKilled, user_id) VALUES (50, 100, 20, 0, 25, 4, 1);
INSERT INTO Player (currentHealth, maxHealth, attack, checkPoint, points, enemiesKilled, user_id) VALUES (100, 100, 20, 0, 25, 4, 2);
INSERT INTO GameObject(type, name, objectPoints) VALUES ('BoostDamage','espada',20);
INSERT INTO GameObject(type, name, objectPoints) VALUES ('BoostDamage','martillo',30);
INSERT INTO GameObject(type, name, objectPoints) VALUES ('BoostLife','poison',10);
INSERT INTO Enemy (type, life, positionX, positionY) VALUES ('monkey',20,3,9);
INSERT INTO Enemy (type, life, positionX, positionY) VALUES ('monkey',50,2,8);
INSERT INTO players_gameobjects (player_id, gameObject_idGameObject) VALUES (1,1);
INSERT INTO players_gameobjects (player_id, gameObject_idGameObject) VALUES (1,2);
INSERT INTO players_gameobjects (player_id, gameObject_idGameObject) VALUES (2,2);
/*INSERT INTO players_enemies (player_id, enemy_idEnemy) VALUES (1,1);
INSERT INTO players_enemies (player_id, enemy_idEnemy) VALUES (1,2);
