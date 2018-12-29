package edu.upc.eetac.dsa.mysql;

import edu.upc.eetac.dsa.exception.UserAlreadyExistsException;
import edu.upc.eetac.dsa.exception.UserNotFoundException;
import edu.upc.eetac.dsa.model.*;
import org.apache.commons.collections.map.MultiValueMap;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ProductManagerImpl implements ProductManager {

    final static Logger log = Logger.getLogger(ProductManagerImpl.class.getName());

    private static ProductManager instance;

    private ProductManagerImpl(){

    }

    public static ProductManager getInstance(){
        if(instance==null) instance = new ProductManagerImpl();
        return instance;
    }

    @Override
    public void clear() {
        instance = null;
    }

    @Override
    public Player logIn(String username, String password) throws UserNotFoundException {
        int idUser = getIdUser(username, password);

        Session session = null;
        Player player = null;

        try{
            session = FactorySession.openSession();
            player = (Player)session.get(Player.class, idUser);
        }
        catch(Exception e){
            log.error("Error trying to open the session: " +e.getMessage());
        }
        finally {
            session.close();
        }

        return player;
    }

    @Override
    public void signUp(String username, String password) throws UserAlreadyExistsException {
        Session session = null;

        try{
            session = FactorySession.openSession();
            User user = new User(username, password);
            boolean exists = session.checkIfUserIsRegistered(User.class, username, password);
            if(exists == false) {
                session.save(user);
                int idUser = getIdUser(username, password);
                Player player = new Player(100, 100, 20, 1, 0, 0, 0, idUser);
                session.save(player);
            }
            else{
                throw new UserAlreadyExistsException();
            }
        }
        catch(Exception e){
            log.error("Error trying to open the session: " +e.getMessage());
            throw new UserAlreadyExistsException();
        }
        finally{
            session.close();
        }
    }


    @Override
    public List<GameObject> getAllObjectsOfAPlayer(int idUser) throws UserNotFoundException {
        Session session = null;
        List<GameObject> foodObjectsList = null;
        List<GameObject> boostDamageObjectsList = null;
        List<GameObject> boostLifeObjectsList = null;
        List<GameObject> gameObjectsList = new ArrayList<>();

        User user = getUser(idUser);

        if(user.getUsername() != null) {
            String query = "SELECT GameObject.* FROM Player, GameObject, Players_Gameobjects ";
            MultiValueMap params = new MultiValueMap();
            params.put("Player.ID", new Condition("=", String.valueOf(idUser)));
            params.put("Player.ID", new Condition("=", "Players_Gameobjects.player_id"));
            params.put("Players_Gameobjects.gameObject_idGameObject", new Condition("=", "GameObject.ID"));
            params.put("GameObject.type", new Condition("=", "'Food'"));

            try {
                session = FactorySession.openSession();
                foodObjectsList = session.query(query, Food.class, params);
                params.remove("GameObject.type");
                params.put("GameObject.type", new Condition("=", "'BoostDamage'"));
                boostDamageObjectsList = session.query(query, BoostDamage.class, params);
                params.remove("GameObject.type");
                params.put("GameObject.type", new Condition("=", "'BoostLife'"));
                boostLifeObjectsList = session.query(query, BoostLife.class, params);
            } catch (Exception e) {
                log.error("Error trying to open the session: " + e.getMessage());
            } finally {
                session.close();
            }

            gameObjectsList.addAll(foodObjectsList);
            gameObjectsList.addAll(boostDamageObjectsList);
            gameObjectsList.addAll(boostLifeObjectsList);
        }
        else{
            throw new UserNotFoundException();
        }

        return gameObjectsList;
    }

    @Override
    public Stats getStatsOfAPlayer(int idUser) throws UserNotFoundException {
        Session session = null;
        Player player;
        Stats stats = new Stats();

        try{
            session = FactorySession.openSession();
            player = (Player)session.get(Player.class, idUser);
        }
        catch(Exception e){
            log.error("Error trying to open the session: " +e.getMessage());
            throw new UserNotFoundException();
        }
        finally {
            session.close();
        }

        stats.setPoints(player.getPoints());
        stats.setEnemiesKilled(player.getEnemiesKilled());

        log.info("Points: " +stats.getPoints());
        log.info("Enemies killed: " +stats.getEnemiesKilled());

        return stats;
    }

    /*@Override
    public void changeObjectInUse(String username, int gameObjectId) throws UserNotFoundException {

    }*/

    @Override
    public void modifyCredentials(String username, String oldpassword, String newpassword) throws UserNotFoundException {
        int idUser = getIdUser(username, oldpassword);

        Session session = null;
        User user;

        try{
            session = FactorySession.openSession();
            user = (User)session.get(User.class, idUser);
            user.setPassword(newpassword);
            session.update(user, idUser);
        }
        catch (Exception e){
            log.error("Error trying to open the session: " +e.getMessage());
        }
        finally {
            session.close();
        }
    }

    @Override
    public User getUser(int idUser) throws UserNotFoundException{
        Session session = null;
        User user;

        try{
            session = FactorySession.openSession();
            user = (User)session.get(User.class, idUser);
        }
        catch(Exception e){
            log.error("Error trying to open the session: " +e.getMessage());
            throw new UserNotFoundException();
        }
        finally {
            session.close();
        }

        return user;
    }

    /*@Override
    public void addWeapon(String username, Weapon myWeapon) throws UserNotFoundException {

    }

    @Override
    public void deleteWeapon(String username, int gameObjectId) throws UserNotFoundException {

    }*/

    @Override
    public void deleteAccount(String username, String password) throws UserNotFoundException {
        int idUser = getIdUser(username, password);

        User user = new User();
        Session session = null;
        Player player;
        Players_Gameobjects players_gameobjects = new Players_Gameobjects();

        HashMap params = new HashMap();

        params.put("player_id", new Condition("=", String.valueOf(idUser)));

        try{
            session = FactorySession.openSession();
            player = (Player)session.get(Player.class, idUser);
            session.delete(players_gameobjects, params);
            session.delete(player, idUser);
            session.delete(user, idUser);
        }
        catch(Exception e){
            log.error("Error trying to open the session: " +e.getMessage());
        }
        finally {
            session.close();
        }
    }

   @Override
    public List<GameObject> getAllObjects() {
       Session session = null;
       List<GameObject> foodObjectsList = null;
       List<GameObject> boostDamageObjectsList = null;
       List<GameObject> boostLifeObjectsList = null;
       List<GameObject> gameObjectsList = new ArrayList<>();

       String query = "SELECT * FROM GameObject ";

       MultiValueMap params = new MultiValueMap();
       params.put("type", new Condition("=", "'Food'"));

       try{
           session = FactorySession.openSession();
           foodObjectsList = session.query(query, Food.class, params);
           params.remove("type");
           params.put("type", new Condition("=", "'BoostDamage'"));
           boostDamageObjectsList = session.query(query, BoostDamage.class, params);
           params.remove("type");
           params.put("type", new Condition("=", "'BoostLife'"));
           boostLifeObjectsList = session.query(query, BoostLife.class, params);
       }
       catch(Exception e){
           log.error("Error trying to open the session: " +e.getMessage());
       }
       finally {
           session.close();
       }

       gameObjectsList.addAll(foodObjectsList);
       gameObjectsList.addAll(boostDamageObjectsList);
       gameObjectsList.addAll(boostLifeObjectsList);

       return gameObjectsList;
    }

    /*@Override
    public void addObject(GameObject gameObject) {

    }

    @Override
    public void updateGameObject(int gameObjectId) throws GameObjectNotFoundException {

    }

    @Override
    public void deleteGameObject(int gameObjectId) throws GameObjectNotFoundException {

    }

    @Override
    public LinkedList<GameMap> getAllGameMaps() {
        return null;
    }

    @Override
    public String getAimOfAGameMap(int gameMapId) throws GameMapNotFoundException {
        return null;
    }

    @Override
    public int getStatus(String username) {
        return 0;
    }

    @Override
    public void saveStatus(int gameMapId, String username) {

    }*/

    private int getIdUser(String username, String password) throws UserNotFoundException{
        Session session = null;

        int idUser;

        try {
            session = FactorySession.openSession();
            idUser = session.getID(User.class, username, password);
        }
        catch(Exception e){
            log.error("The user doesn't exist: " +e.getMessage());
            throw new UserNotFoundException();
        }
        finally {
            session.close();
        }

        return idUser;
    }
}
