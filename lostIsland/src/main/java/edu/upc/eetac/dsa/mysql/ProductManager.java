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
     *@return Player class
     *@throws UserNotFoundException if the User doesn't exist
     *@throws UserAlreadyConectedException if the user is already conected
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
     *
     */
    void modifyCredentials(String username, String oldpassword, String newpassword) throws UserNotFoundException;
    /**
     * @param idUser id of the user
     * @throws UserNotFoundException if the User doesn't exist
     */
    void logOut(int idUser) throws UserNotFoundException;

    //**2. Users

    /**Ends a game of a user and update the fields
     *
     *@param idUser id of the user
     *@throws UserNotFoundException if the User doesn't exist
     *
     */
    void finishPlayerGame(int idUser) throws UserNotFoundException;
    /**Show list of all GameObjects of a given user
     *
     *@param idUser id of the user
     *@return linkedlist of objects
     *@throws UserNotFoundException if the User doesn't exist
     *
     */
    List<GameObject> getAllObjectsOfAPlayer(int idUser) throws UserNotFoundException;
    /**Show list of remaining enemies of a given user
     *
     *@param idUser id of the user
     *@throws UserNotFoundException if the User doesn't exist
     *@return linkedlist of enemies
     *@throws UserNotFoundException if the User doesn't exist
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
    /**Remove a part of antenna from the gameObjectList of a User in case that this User pick this part in the game
     *
     *@param idUser id of the user
     *@param idGameObject id of the user
     *@throws UserNotFoundException if the User doesn't exist
     *@throws GameObjectNotFoundException if the GameObject doesn't exist
     *
     */
    void removeAntennaPartOfAPlayer(int idUser, int idGameObject) throws UserNotFoundException, GameObjectNotFoundException;
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
     *@throws UserNotFoundException if the User doesn't exist
     *
     */
    Stats getStatsOfAPlayer(int idUser) throws UserNotFoundException;
    /**Get User passing its ID
     * @param idUser id of the user (integer)
     * @return User user class
     * @throws UserNotFoundException if the User doesn't exist
     */
    User getUser(int idUser) throws UserNotFoundException;
    /**Get Player passing its ID
     * @param idUser id of the user (integer)
     * @return Player player class
     * @throws UserNotFoundException if the User doesn't exist
     */
    Player getPlayer(int idUser) throws UserNotFoundException;
    /**Get ID of the user passing its username and password
     * @param username username of the user
     * @param password password of the user
     * @return integer id of the user
     * @throws UserNotFoundException if the User doesn't exist
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
     *@throws UserNoMoneyException if the user doesn't has enough money to buy the object
     *@throws GameObjectBoostDamageAlreadyInUseException if the object that we want to buy exists in our Inventory
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
     * @param buy if the user buys or sell an object
     * @throws UserNotFoundException if the User doesn't exist
     * @throws GameObjectNotFoundException if the Object doesn't exist
     */
    void modifyAttributes(int idGameObject, int idUser, boolean buy) throws UserNotFoundException, GameObjectNotFoundException;
    /**
     * @param enemieskilled enemieskilled of the user
     * @param idUser id of the user
     * @throws UserNotFoundException
     */
    void updateUserEnemiesKilled(int idUser, int enemieskilled) throws UserNotFoundException;
    /**Sell an object and remove it from my Inventory
     *
     *@param idUser id of the user
     *@param idGameObject id of the object
     *@param points points of the user
     *@param costObject cost of the object
     *@throws UserNotFoundException if the User doesn't exist
     *@throws GameObjectNotFoundException if the Object doesn't exist
     *
     */
    void sellObject(int idUser, int idGameObject, int points, int costObject) throws UserNotFoundException, GameObjectNotFoundException;
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
