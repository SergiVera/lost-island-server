package edu.upc.eetac.dsa;

import edu.upc.eetac.dsa.model.Player;

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
     Player logIn(String username, String password) throws UserNotFoundException;
    /**Creates a new user
     *
     *@param username name of the user
     *@param password password of the user
     *@throws UserAlreadyExistsException if the user exists
     */
     void signUp(String username, String password) throws UserAlreadyExistsException;

    //**2. Users

    /**Show list of all GameObjects of a given player
     *
     *@param username name of the user
     *@throws UserNotFoundException if the User doesn't exist
     *@return linkedlist of objects
     *
     *//*
    LinkedList<GameObject> getAllObjectsOfAPlayer(String username) throws UserNotFoundException;
    *//**Show the stats of a given player
     *
     *@param username name of the user
     *@throws UserNotFoundException if the User doesn't exist
     *@return Stats class
     *
     *//*
    Stats getStatsOfAPlayer(String username) throws UserNotFoundException;
    *//**Change the current object in use
     *
     *@param username name of the user
     *@param gameObjectId id of the GameObject that we want to use
     *@throws UserNotFoundException if the User doesn't exist
     *@throws ?? 400 bad request
     *
     *//*
    void changeObjectInUse(String username, int gameObjectId) throws UserNotFoundException;
    *//**Change the username and the password of a given user
     *
     *@param username name of the user
     *@param password password of the user
     *@throws UserNotFoundException if the User doesn't exist
     *@throws ?? 400 bad request
     *
     */
    void modifyCredentials(String username, String password) throws UserNotFoundException;
    /**Add new weapon to my Inventory
     *
     *@param username name of the user
     *@throws UserNotFoundException if the User doesn't exist
     *@throws ?? 400 bad request
     *
     *//*
    void addWeapon(String username, Weapon myWeapon) throws UserNotFoundException;

    *//**We can add more GameObjects to my Inventory??
     /**In the POSTS & PUTS we have to return the modified | created fields??

     /**Remove weapon of my Inventory
     *
     *@param username name of the user
     *@param gameObjectId id of the GameObject that we want to delete
     *@throws UserNotFoundException if the User doesn't exist
     *@throws ?? 400 bad request
     *
     *//*
    void deleteWeapon(String username, int gameObjectId) throws UserNotFoundException;
    *//**Cancel my account
     *
     *@param username name of the user
     *@throws UserNotFoundException if the User doesn't exist
     *
     *//*
    void deleteAccount(String username) throws UserNotFoundException;

    *//**3. Objects

     /**Show list of all objects
     *
     *@return linkedlist of objects
     *
     *//*
    LinkedList<Object> getAllObjects();
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

    *//**4. GameMaps

     /**Show list of all GameMaps in the game
     *
     *@return linkedlist of GameMaps
     *
     *//*
    LinkedList<GameMap> getAllGameMaps();
    *//**Show the aim of each GameMap given its GameMapId
     *
     *@param gameMapId id of the GameMap
     *@throws GameMapNotFoundException if the GameMap doesn't exist
     *@return aim String of the aim of the map
     *
     *//*
    String getAimOfAGameMap(int gameMapId) throws GameMapNotFoundException;
    *//**Get the gameMapId where the player ended its last game
     *
     *@param username username of the player
     *@return integer gameMapId
     *
     *//*
    int getStatus(String username);
    *//**Save the position of the player in the map
     *
     *@param gameMapId id of the GameMap
     *@param username  username of the player in the current game
     *
     *//*
    void saveStatus(int gameMapId, String username);

    *//**
     * clear all the data structures
     *//*
    void clear();
*/
}
