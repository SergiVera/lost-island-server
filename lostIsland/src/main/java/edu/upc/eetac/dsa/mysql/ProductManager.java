package edu.upc.eetac.dsa.mysql;

import edu.upc.eetac.dsa.exception.*;
import edu.upc.eetac.dsa.model.GameObject;
import edu.upc.eetac.dsa.model.Player;
import edu.upc.eetac.dsa.model.Stats;
import edu.upc.eetac.dsa.model.User;

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
     *@param password password of the user
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
     *@param username name of the user
     *@throws UserNotFoundException if the User doesn't exist
     *@return linkedlist of objects
     *
     */
    List<GameObject> getAllObjectsOfAPlayer(int idUser) throws UserNotFoundException;
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
    /**Get ID of the user passing its username and password
     * @param username username of the user
     * @param password password of the user
     * @return integer id of the user
     */
    int getIdUser(String username, String password) throws UserNotFoundException;
    /**Add new weapon to my Inventory
     *
     *@param username name of the user
     *@throws UserNotFoundException if the User doesn't exist
     *@throws ?? 400 bad request
     *
     *//*
    void addWeapon(String username, Weapon myWeapon) throws UserNotFoundException;

    *//**We can add more GameObjects to my Inventory??
     /* *In the POSTS & PUTS we have to return the modified | created fields??

     /**
     * @param points points of the user
     * @param idUser id of the user
     * @throws UserNotFoundException
     */
    void updateUserPoints(int idUser, int points) throws UserNotFoundException;

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
