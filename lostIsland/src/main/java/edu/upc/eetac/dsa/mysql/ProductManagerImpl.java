package edu.upc.eetac.dsa.mysql;

import edu.upc.eetac.dsa.exception.*;
import edu.upc.eetac.dsa.model.*;
import org.apache.commons.collections.map.MultiValueMap;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
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
    public Player logIn(String username, String password) throws UserNotFoundException, UserAlreadyConectedException {
        int idUser = getIdUser(username, password);

        Session session = null;
        Player player;
        User user = null;

        try{
            session = FactorySession.openSession();
            user = (User)session.get(User.class, idUser);
        }
        catch(Exception e){
            log.error("Error trying to open the session: " +e.getMessage());
        }
        finally {
            session.close();
        }

        if(user.getConected() == false){
            user.setConected(true);

            try {
                session = FactorySession.openSession();
                session.update(user, idUser);
                player = (Player)session.get(Player.class, idUser);
            }
            catch (Exception e) {
                log.error("Error trying to open the session: " +e.getMessage());
                throw new UserNotFoundException();
            }
            finally {
                session.close();
            }
        }
        else{
            throw new UserAlreadyConectedException();
        }

        return player;
    }

    @Override
    public void signUp(String username, String password) throws UserAlreadyExistsException {
        Session session = null;

        try{
            session = FactorySession.openSession();
            User user = new User(username, password, false);
            boolean exists = session.checkIfUserIsRegistered(User.class, username, password);
            if(exists == false) {
                session.save(user);
                int idUser = getIdUser(username, password);
                Player player = new Player(100, 100, 20, 0, 0, 0, idUser);
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
    public void modifyCredentials(String username, String oldpassword, String newpassword) throws UserNotFoundException {
        int idUser = getIdUser(username, oldpassword);

        Session session = null;
        User user;
        Credentials credentials = new Credentials();
        credentials.setUsername(username);
        credentials.setOldpassword(oldpassword);
        credentials.setNewpassword(newpassword);

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
    public void logOut(int idUser) throws UserNotFoundException{
        Session session = null;
        User user;

        try {
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

        user.setConected(false);

        try {
            session = FactorySession.openSession();
            session.update(user, idUser);
        }
        catch (Exception e) {
            log.error("Error trying to open the session: " +e.getMessage());
            throw new UserNotFoundException();
        }
        finally {
            session.close();
        }
    }

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
    public List<Enemy> getAllEnemiesOfAPlayer(int idUser) throws UserNotFoundException{
        Session session = null;
        List<Enemy> monkeyEnemyList = null;
        List<Enemy> bossEnemyList = null;
        List<Enemy> enemiesList = new ArrayList<>();

        User user = getUser(idUser);

        if(user.getUsername() != null){
            String query = "SELECT Enemy.* FROM Player, Enemy, Players_Enemies ";
            MultiValueMap params = new MultiValueMap();
            params.put("Player.ID", new Condition("=", String.valueOf(idUser)));
            params.put("Player.ID", new Condition("=", "Players_Enemies.player_id"));
            params.put("Players_Enemies.enemy_idEnemy", new Condition("=", "Enemy.ID"));
            params.put("Enemy.type", new Condition("=", "'Monkey'"));

            try {
                session = FactorySession.openSession();
                monkeyEnemyList = session.query(query, Monkey.class, params);
                params.remove("Enemy.type");
                params.put("Enemy.type", new Condition("=", "'Boss'"));
                bossEnemyList = session.query(query, Boss.class, params);
            } catch (Exception e) {
                log.error("Error trying to open the session: " + e.getMessage());
            } finally {
                session.close();
            }

            enemiesList.addAll(monkeyEnemyList);
            enemiesList.addAll(bossEnemyList);
        }
        else{
            throw new UserNotFoundException();
        }

        return enemiesList;
    }

    @Override
    public Stats getStatsOfAPlayer(int idUser) throws UserNotFoundException {
        Session session = null;
        Player player;
        User user;
        Stats stats = new Stats();

        try{
            session = FactorySession.openSession();
            player = (Player)session.get(Player.class, idUser);
            user = (User)session.get(User.class, idUser);
        }
        catch(Exception e){
            log.error("Error trying to open the session: " +e.getMessage());
            throw new UserNotFoundException();
        }
        finally {
            session.close();
        }

        stats.setUsername(user.getUsername());
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

    @Override
    public Player getPlayer (int idUser) throws UserNotFoundException{
        Session session = null;
        Player player;

        try{
            session = FactorySession.openSession();
            player = (Player) session.get(Player.class, idUser);
        }
        catch(Exception e){
            log.error("Error trying to open the session: " +e.getMessage());
            throw new UserNotFoundException();
        }
        finally {
            session.close();
        }

        return player;
    }

   @Override
    public int getIdUser(String username, String password) throws UserNotFoundException{
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

    //This method is called when a player buys an object at the shop
    @Override
    public void buyObject(int idUser, int idGameObject, int points, int costObject) throws UserNotFoundException, GameObjectNotFoundException, UserNoMoneyException, GameObjectBoostDamageAlreadyInUseException {
        List<GameObject> gameObjectList;
        List<GameObject> boostDamageObjectList;
        boolean exists = false;

        //Get all objects of the user
        gameObjectList = this.getAllObjectsOfAPlayer(idUser);

        //Get single object we want to add
        boostDamageObjectList = this.getAllBoostDamage();

        //Get single object passing its id
        GameObject singleObject = this.getSingleObject(idGameObject);

        for(GameObject go : gameObjectList){
            for(GameObject gameObject : boostDamageObjectList) {
                log.info("gameObjectList: type " + go.getType() + " name " + go.getName());
                log.info("boostDamageObjectList: type " + gameObject.getType() + " name " + gameObject.getName());
                exists = (go.getType().equals(gameObject.getType())) && (go.getName().equals(gameObject.getName())) && (singleObject.getName().equals(go.getName()));
                log.info("Exists: " +exists);
                if(exists == true){
                    break;
                }
            }
            if(exists == true){
                break;
            }
        }

        if(exists == false) {
            if (points >= costObject) {
                points -= costObject;
                updateUserPoints(idUser, points);
                modifyAttributes(idGameObject, idUser);
            }
            else
                throw new UserNoMoneyException();
        }
        else
            throw new GameObjectBoostDamageAlreadyInUseException();
    }

    private List<GameObject> getAllBoostDamage() {
        Session session = null;

        List<GameObject> boostDamageObjectsList = null;

        String query = "SELECT * FROM GameObject ";

        MultiValueMap params = new MultiValueMap();
        params.put("type", new Condition("=", "'BoostDamage'"));

        try{
            session = FactorySession.openSession();
            boostDamageObjectsList = session.query(query, BoostDamage.class, params);
        }
        catch(Exception e){
            log.error("Error trying to open the session: " +e.getMessage());
        }
        finally {
            session.close();
        }

        return boostDamageObjectsList;
    }

    private GameObject getSingleObject(int idGameObject) throws GameObjectNotFoundException {
        Session session = null;
        GameObject gameObject;
        BoostDamage boostDamage = null;

        String query = "SELECT * FROM GameObject ";

        MultiValueMap params = new MultiValueMap();
        params.put("ID", new Condition("=", String.valueOf(idGameObject)));

        try{
            session = FactorySession.openSession();
            boostDamage = (BoostDamage) session.singleQuery(query, BoostDamage.class, params);
        }
        catch(Exception e){
            log.error("Error trying to open the session: " +e.getMessage());
            throw new GameObjectNotFoundException();
        }
        finally {
            session.close();
        }

        gameObject = boostDamage;

        return gameObject;
    }

    //This method is called when a player grabs an object in the game
    @Override
    public void modifyAttributes(int idGameObject, int idUser) throws UserNotFoundException, GameObjectNotFoundException{
        Session session = null;
        List<GameObject> gameObjectList;
        GameObject object = null;
        Player player;

        try {
            session = FactorySession.openSession();
            player = (Player) session.get(Player.class, idUser);
        } catch (Exception e) {
            log.error("The user doesn't exist: " + e.getMessage());
            throw new UserNotFoundException();
        } finally {
            session.close();
        }

        gameObjectList = getAllObjects();

        for (GameObject gameObject : gameObjectList) {
            if (gameObject.getID() == idGameObject) {
                object = gameObject;
            }
        }

        if(object != null) {

            player = object.modifyAttributes(player);

            //Modify listobjects

            try {
                session = FactorySession.openSession();
                Players_Gameobjects players_gameobjects = new Players_Gameobjects(idUser, idGameObject);
                session.customSave(players_gameobjects);
            } catch (Exception e) {
                log.error("The user doesn't exist: " + e.getMessage());
                throw new UserNotFoundException();
            } finally {
                session.close();
            }

            //Update database

            try {
                session = FactorySession.openSession();
                session.update(player, idUser);
            } catch (Exception e) {
                log.error("Error trying to open the session: " + e.getMessage());
                throw new UserNotFoundException();
            } finally {
                session.close();
            }
        }
        else{
            throw new GameObjectNotFoundException();
        }
    }

    @Override
    public void updateUserPoints(int idUser, int points) throws UserNotFoundException{
        Session session = null;
        Player player;

        try {
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

        player.setPoints(points);

        try {
            session = FactorySession.openSession();
            session.update(player, idUser);
        }
        catch (Exception e) {
            log.error("Error trying to open the session: " +e.getMessage());
            throw new UserNotFoundException();
        }
        finally {
            session.close();
        }
    }

    @Override
    public void updateUserEnemiesKilled(int idUser, int enemieskilled) throws UserNotFoundException{
        Session session = null;
        Player player;

        try {
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

        player.setEnemiesKilled(enemieskilled);

        try {
            session = FactorySession.openSession();
            session.update(player, idUser);
        }
        catch (Exception e) {
            log.error("Error trying to open the session: " +e.getMessage());
            throw new UserNotFoundException();
        }
        finally {
            session.close();
        }
    }

    /*@Override
    public void deleteWeapon(String username, int gameObjectId) throws UserNotFoundException {

    }*/

    @Override
    public List<Stats> getStats(){
        Session session = null;
        List<Player> playerList = null;
        List<Stats> statsList = new LinkedList<>();
        Player player;
        User user = null;

        try{
            session = FactorySession.openSession();
            playerList = session.findAll(Player.class);
        }
        catch(Exception e){
            log.error("Error trying to open the session: " +e.getMessage());
        }
        finally {
            session.close();
        }

        for(int i = 0; i<playerList.size(); i++){
            try {
                session = FactorySession.openSession();
                int idUser = playerList.get(i).getUser_id();
                user = (User) session.get(User.class, idUser);
            }
            catch(Exception e){
                log.error("Error trying to open the session: " +e.getMessage());
            }
            finally {
                session.close();
            }
            Stats stats = new Stats();
            stats.setUsername(user.getUsername());
            stats.setEnemiesKilled(playerList.get(i).getEnemiesKilled());
            stats.setPoints(playerList.get(i).getPoints());
            statsList.add(stats);
        }

        return statsList;
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

    }*/

    @Override
    public int getStatus(int idUser) {
        return 0;
    }

    @Override
    public void saveStatus(int gameMapId, int idUser) throws UserNotFoundException {
        Session session = null;
        Player player;

        try {
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

        player.setCheckPoint(gameMapId);

        try {
            session = FactorySession.openSession();
            session.update(player, idUser);
        }
        catch (Exception e) {
            log.error("Error trying to open the session: " +e.getMessage());
            throw new UserNotFoundException();
        }
        finally {
            session.close();
        }
    }

    @Override
    public List<Enemy> getAllEnemies(){
        Session session = null;
        List<Enemy> monkeyEnemyList = null;
        List<Enemy> bossEnemyList = null;
        List<Enemy> mapEnemiesList = new ArrayList<>();

        String query = "SELECT * FROM Enemy ";

        MultiValueMap params = new MultiValueMap();
        params.put("type", new Condition("=", "'Monkey'"));

        try{
            session = FactorySession.openSession();
            monkeyEnemyList = session.query(query, Monkey.class, params);
            params.remove("type");
            params.put("type", new Condition("=", "'Boss'"));
            bossEnemyList = session.query(query, Boss.class, params);
        }
        catch(Exception e){
            log.error("Error trying to open the session: " +e.getMessage());
        }
        finally {
            session.close();
        }

        for(int i = 0; i<monkeyEnemyList.size(); i++){
            Monkey monkey = new Monkey();
            monkey.setType(monkeyEnemyList.get(i).getType());
            monkey.setLife(monkeyEnemyList.get(i).getLife());
            monkey.setMap(monkeyEnemyList.get(i).getMap());
            mapEnemiesList.add(monkey);
        }

        for(int i = 0; i<bossEnemyList.size(); i++){
            Boss boss = new Boss();
            boss.setType(bossEnemyList.get(i).getType());
            boss.setLife(bossEnemyList.get(i).getLife());
            boss.setMap(monkeyEnemyList.get(i).getMap());
            mapEnemiesList.add(boss);
        }

        return mapEnemiesList;
    }
}
