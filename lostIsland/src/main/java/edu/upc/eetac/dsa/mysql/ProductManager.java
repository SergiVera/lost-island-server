package edu.upc.eetac.dsa.mysql;

import edu.upc.eetac.dsa.exception.*;
import edu.upc.eetac.dsa.model.*;

import java.util.List;

public interface ProductManager {
    /**Methods

    *API Services:

    *1. Authentication
    */

    /**Load the user profile
     *
     *@param username name of the user
     *@param password password of the user
     *@throws UserNotFoundException if the User doesn't exist
     *@return Player class
     */
     Player logIn(String username, String password) throws UserNotFoundException, UserAlreadyConectedException;
    /**Creates a new user
     *
     *@param username name of the user
     *@param password password of the user
     *@throws UserAlreadyExistsException if the user exists
     */
     void signUp(String username, String password) throws UserAlreadyExistsException;
    /**Cancel my account
     *
     *@param username name of the user
     *@throws UserNotFoundException if the User doesn't exist
     *
     */
    void deleteAccount(String username, String password) throws UserNotFoundException;
    /**Change the username and the password of a given user
     *
     *@param username name of the user
     *@param oldpassword old password of the user
     *@param newpassword new password of the user
     *@throws UserNotFoundException if the User doesn't exist
     *@throws ?? 400 bad request
     *
     */
    void modifyCredentials(String username, String oldpassword, String newpassword) throws UserNotFoundException;
    /**
     * @param idUser id of the user
     * @throws UserNotFoundException if the User doesn't exist
     */
    void logOut(int idUser) throws UserNotFoundException;

    //**2. Users

    /**Show list of all GameObjects of a given user
     *
     *@param idUser id of the user
     *@throws UserNotFoundException if the User doesn't exist
     *@return linkedlist of objects
     *
     */
    List<GameObject> getAllObjectsOfAPlayer(int idUser) throws UserNotFoundException;
    /**Show list of remaining enemies of a given user
     *
     *@param idUser id of the user
     *@throws UserNotFoundException if the User doesn't exist
     *@return linkedlist of enemies
     *
     */
    List<Enemy> getAllEnemiesOfAPlayer(int idUser) throws UserNotFoundException;
    /**Remove an enemy from the enemiesList of a User in case that this User kills an enemy
     *
     *@param idUser id of the user
     *@param idEnemy id of the user
     *@throws UserNotFoundException if the User doesn't exist
     *@throws EnemyNotFoundException if the Enemy doesn't exist
     *
     */
    void removeEnemyOfAPlayer(int idUser, int idEnemy) throws UserNotFoundException, EnemyNotFoundException;
    /**Update an enemy from the enemiesList of a User
     *
     *@param idUser id of the user
     *@param idEnemy id of the user
     *@throws UserNotFoundException if the User doesn't exist
     *@throws EnemyNotFoundException if the Enemy doesn't exist
     *
     */
    void updateEnemyOfAPlayer(int idUser, int idEnemy, int life, int PositionX, int PositionY) throws UserNotFoundException, EnemyNotFoundException;
    /**Show the stats of a given user
     *
     *@param idUser id of the user
     *@throws UserNotFoundException if the User doesn't exist
     *@return Stats class
     *
     */
    Stats getStatsOfAPlayer(int idUser) throws UserNotFoundException;/*
    *//**Change the current object in use
     *
     *@param username name of the user
     *@param gameObjectId id of the GameObject that we want to use
     *@throws UserNotFoundException if the User doesn't exist
     *@throws ?? 400 bad request
     *
     *//*
    void changeObjectInUse(String username, int gameObjectId) throws UserNotFoundException;
    */
    /**Get User passing its ID
     * @param idUser id of the user (integer)
     * @return User user class
     */
    User getUser(int idUser) throws UserNotFoundException;
    /**Get Player passing its ID
     * @param idUser id of the user (integer)
     * @return Player player class
     */
    Player getPlayer(int idUser) throws UserNotFoundException;
    /**Get ID of the user passing its username and password
     * @param username username of the user
     * @param password password of the user
     * @return integer id of the user
     */
    int getIdUser(String username, String password) throws UserNotFoundException;
    /**Buy new object and add it to my Inventory
     *
     *@param idUser id of the user
     *@param idGameObject id of the object
     *@param points points of the user
     *@param costObject cost of the object
     *@throws UserNotFoundException if the User doesn't exist
     *@throws GameObjectNotFoundException if the Object doesn't exist
     *
     */
    void buyObject(int idUser, int idGameObject, int points, int costObject) throws UserNotFoundException, GameObjectNotFoundException, UserNoMoneyException, GameObjectBoostDamageAlreadyInUseException;
     /**
     * @param points points of the user
     * @param idUser id of the user
     * @throws UserNotFoundException
     */
    void updateUserPoints(int idUser, int points) throws UserNotFoundException;
    /**
     * @param idGameObject id of the object
     * @param idUser id of the user
     */
    void modifyAttributes(int idGameObject, int idUser) throws UserNotFoundException, GameObjectNotFoundException;
    /**
     * @param enemieskilled enemieskilled of the user
     * @param idUser id of the user
     * @throws UserNotFoundException
     */
    void updateUserEnemiesKilled(int idUser, int enemieskilled) throws UserNotFoundException;

    /**Remove weapon of my Inventory
    *
    *@param username name of the user
    *@param gameObjectId id of the GameObject that we want to delete
    *@throws UserNotFoundException if the User doesn't exist
    *@throws ?? 400 bad request
    *
    *//*
    void deleteWeapon(String username, int gameObjectId) throws UserNotFoundException;
    */
    /**
     * @return List of stats
     */
    List<Stats> getStats();

    //**3. Objects

     /**Show list of all objects
     *
     *@return List of objects
     *
     */
    List<GameObject> getAllObjects();/*
    *//**Add new object to the game
     *
     *@param gameObject class
     *@throws ?? 400 bad request
     *
     *//*
    void addObject(GameObject gameObject);
    *//**Change the specs of an existing GameObject given its GameObjectId
     *
     *@param gameObjectId id of the GameObject that we want to use
     *@throws GameObjectNotFoundException if the GameObject doesn't exist
     *@throws ?? 400 bad request
     *
     *//*
    void updateGameObject(int gameObjectId) throws GameObjectNotFoundException;
    *//**Remove GameObject
     *
     *@param gameObjectId id of the GameObject that we want to delete
     *@throws GameObjectNotFoundException if the GameObject doesn't exist
     *
     *//*
    void deleteGameObject(int gameObjectId) throws GameObjectNotFoundException;

    //4. GameMaps

    *//**Get the gameMapId where the user ended its last game
     *
     *@param idUser id of the user
     *@return integer gameMapId
     *
     */
    int getStatus(int idUser);
    /**Save the position of the user in the map
     *
     *@param gameMapId id of the GameMap
     *@param idUser  id of the user in the current game
     *
     */
    void saveStatus(int gameMapId, int idUser) throws UserNotFoundException;

    /**
     * clear all the data structures
     */
    void clear();
}
