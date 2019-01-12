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
                session.update(user, idUser, false);
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
        List<GameObject> antenna;

        try{
            session = FactorySession.openSession();
            User user = new User(username, password, false);
            boolean exists = session.checkIfUserIsRegistered(User.class, username, password);
            if(exists == false) {
                session.save(user);
                int idUser = getIdUser(username, password);
                Player player = new Player(6, 6, 1, 1, 0, 0, 1, idUser);
                session.save(player);
                for(int i=1; i<4; i++) {
                    Monkey monkey = new Monkey();
                    monkey.setType("Monkey");
                    monkey.setLife(2);
                    monkey.setMap(i);
                    monkey.setPositionX(3);
                    monkey.setPositionY(9);
                    monkey.setPlayer_id(idUser);
                    session.customSave(monkey, true);
                }
                Boss boss = new Boss();
                boss.setType("Boss");
                boss.setLife(10);
                boss.setMap(4);
                boss.setPositionX(2);
                boss.setPositionY(8);
                boss.setPlayer_id(idUser);
                session.customSave(boss, true);
                MultiValueMap params = new MultiValueMap();
                params.put("type",new Condition("=", "'Antenna'"));
                String query = "SELECT * FROM GameObject ";
                antenna = session.query(query, Antenna.class, params);
                for (GameObject gameObject : antenna) {
                    Players_Gameobjects players_gameobjects = new Players_Gameobjects(idUser, gameObject.getID());
                    session.customSave(players_gameobjects, false);
                }
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
            session.update(user, idUser, false);
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
            user.setConected(false);
            session.update(user, idUser, false);
        }
        catch(Exception e){
            log.error("Error trying to open the session: " +e.getMessage());
            throw new UserNotFoundException();
        }
        finally {
            session.close();
        }
    }

    @Override
    public void finishPlayerGame(int idUser) throws UserNotFoundException {
        Session session = null;
        Monkey monkey = new Monkey();
        Boss boss = new Boss();
        List<Antenna> antennaList;
        Player player;
        List<GameObject> antennaObjectsList;

        player = getPlayer(idUser);

        if(player.getUser_id() != 0) {
            player.setLevel(player.getLevel() + 1);

            try {
                session = FactorySession.openSession();
                session.update(player, idUser, false);
            } catch (Exception e) {
                log.error("Error trying to open the session: " + e.getMessage());
                throw new UserNotFoundException();
            } finally {
                session.close();
            }

            player = getPlayer(idUser);

            HashMap params = new HashMap();

            params.put("player_id", new Condition("=", String.valueOf(idUser)));

            try {
                session = FactorySession.openSession();
                session.delete(monkey, params);
                for (int i = 1; i < 4; i++) {
                    monkey.setType("Monkey");
                    monkey.setLife(2 * player.getLevel());
                    monkey.setMap(i);
                    monkey.setPositionX(3);
                    monkey.setPositionY(9);
                    monkey.setPlayer_id(idUser);
                    session.customSave(monkey, true);
                }
                boss.setType("Boss");
                boss.setLife(10 * player.getLevel());
                boss.setMap(4);
                boss.setPositionX(2);
                boss.setPositionY(8);
                boss.setPlayer_id(idUser);
                session.customSave(boss, true);
                String query = "SELECT GameObject.* FROM Player, GameObject, Players_Gameobjects ";
                MultiValueMap params2 = new MultiValueMap();
                params2.put("Player.ID", new Condition("=", String.valueOf(idUser)));
                params2.put("Player.ID", new Condition("=", "Players_Gameobjects.player_id"));
                params2.put("Players_Gameobjects.gameObject_idGameObject", new Condition("=", "GameObject.ID"));
                params2.put("GameObject.type", new Condition("=", "'Antenna'"));
                antennaObjectsList = session.query(query, Antenna.class, params2);
                for(GameObject gameObject : antennaObjectsList){
                    HashMap params3 = new HashMap();
                    params3.put("player_id", new Condition("=", String.valueOf(idUser)));
                    params3.put("gameObject_idGameObject", new Condition("=", String.valueOf(gameObject.getID())));
                    Players_Gameobjects players_gameobjects = new Players_Gameobjects(idUser, gameObject.getID());
                    session.delete(players_gameobjects, params3);
                }
                MultiValueMap params4 = new MultiValueMap();
                params4.put("type", new Condition("=", "'Antenna'"));
                String query2 = "SELECT * FROM GameObject ";
                antennaList = session.query(query2, Antenna.class, params4);
                for (GameObject gameObject : antennaList) {
                    Players_Gameobjects players_gameobjects = new Players_Gameobjects(idUser, gameObject.getID());
                    session.customSave(players_gameobjects, false);
                }
                updateUserPoints(idUser, player.getPoints() + 10);
            } catch (Exception e) {
                log.error("Error trying to open the session: " + e.getMessage());
                throw new UserNotFoundException();
            } finally {
                session.close();
            }
        }
        else{
            throw new UserNotFoundException();
        }

    }

    @Override
    public void deleteAccount(String username, String password) throws UserNotFoundException {
        int idUser = getIdUser(username, password);

        User user = new User();
        Session session = null;
        Player player;
        Players_Gameobjects players_gameobjects = new Players_Gameobjects();
        Monkey monkey = new Monkey();

        HashMap params = new HashMap();

        params.put("player_id", new Condition("=", String.valueOf(idUser)));

        try{
            session = FactorySession.openSession();
            player = (Player)session.get(Player.class, idUser);
            session.delete(monkey, params);
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
        List<GameObject> antennaObjectsList = null;
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
                params.remove("GameObject.type");
                params.put("GameObject.type", new Condition("=", "'Antenna'"));
                antennaObjectsList = session.query(query, Antenna.class, params);
            } catch (Exception e) {
                log.error("Error trying to open the session: " + e.getMessage());
            } finally {
                session.close();
            }

            gameObjectsList.addAll(foodObjectsList);
            gameObjectsList.addAll(boostDamageObjectsList);
            gameObjectsList.addAll(boostLifeObjectsList);
            gameObjectsList.addAll(antennaObjectsList);
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
            String query = "SELECT * FROM Enemy ";
            MultiValueMap params = new MultiValueMap();
            params.put("player_id", new Condition("=", String.valueOf(idUser)));
            params.put("type", new Condition("=", "'Monkey'"));
            try {
                session = FactorySession.openSession();
                monkeyEnemyList = session.query(query, Monkey.class, params);
                params.remove("type");
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
    public void removeEnemyOfAPlayer(int idUser, int idEnemy) throws UserNotFoundException, EnemyNotFoundException {
        Enemy enemy = getSingleEnemy(idEnemy);

        Session session = null;

        HashMap params = new HashMap();

        params.put("player_id", new Condition("=", String.valueOf(idUser)));
        params.put("ID", new Condition("=", String.valueOf(idEnemy)));

        try{
            session = FactorySession.openSession();
            session.delete(enemy, params);
        }
        catch(Exception e){
            log.error("Error trying to open the session: " +e.getMessage());
            throw new UserNotFoundException();
        }
        finally {
            session.close();
        }
    }

    @Override
    public void removeAntennaPartOfAPlayer(int idUser, int idGameObject) throws UserNotFoundException, GameObjectNotFoundException {
        modifyAttributes(idGameObject, idUser, false);
    }

    @Override
    public void updateEnemyOfAPlayer(int idUser, int idEnemy, int life, int PositionX, int PositionY) throws UserNotFoundException, EnemyNotFoundException {
        Session session = null;
        List<Enemy> enemyList;
        Enemy object = null;

        enemyList = getAllEnemiesOfAPlayer(idUser);

        for (Enemy enemy : enemyList) {
            if (enemy.getID() == idEnemy) {
                object = enemy;
            }
        }

        if(object != null) {
            object.setLife(life);
            object.setPositionX(PositionX);
            object.setPositionY(PositionY);

            //Update database
            try {
                session = FactorySession.openSession();
                session.update(object, idEnemy, true);
            } catch (Exception e) {
                log.error("Error trying to open the session: " + e.getMessage());
                throw new UserNotFoundException();
            } finally {
                session.close();
            }
        }
        else{
            throw new EnemyNotFoundException();
        }
    }

    private Enemy getSingleEnemy(int idEnemy) throws EnemyNotFoundException {
        Session session = null;
        Enemy enemy = null;
        Monkey monkey;
        Boss boss;
        String result;

        String query1 =  "SELECT IF((SELECT Type FROM Enemy WHERE ID="+idEnemy+")='Monkey', 'YES', 'NO')";
        String query2 =  "SELECT IF((SELECT Type FROM Enemy WHERE ID="+idEnemy+")='Boss', 'YES', 'NO')";

        String query = "SELECT * FROM Enemy ";

        MultiValueMap params = new MultiValueMap();
        params.put("ID", new Condition("=", String.valueOf(idEnemy)));

        try{
            session = FactorySession.openSession();
            result = session.customQuery(query1);
            if(result.equals("YES")){
                monkey = (Monkey) session.singleQuery(query, Monkey.class, params);
                enemy = monkey;
            }
            result = session.customQuery(query2);
            if(result.equals("YES")){
                boss = (Boss) session.singleQuery(query, Boss.class, params);
                enemy = boss;
            }
        }
        catch(Exception e){
            log.error("Error trying to open the session: " +e.getMessage());
            throw new EnemyNotFoundException();
        }
        finally {
            session.close();
        }

        return enemy;
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
        stats.setLevel(player.getLevel());

        log.info("Points: " +stats.getPoints());
        log.info("Enemies killed: " +stats.getEnemiesKilled());

        return stats;
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
                modifyAttributes(idGameObject, idUser, true);
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
        GameObject gameObject = null;
        BoostDamage boostDamage;
        BoostLife boostLife;
        Food food;
        Antenna antenna;
        String result;

        String query1 =  "SELECT IF((SELECT Type FROM GameObject WHERE ID="+idGameObject+")='BoostLife', 'YES', 'NO')";
        String query2 =  "SELECT IF((SELECT Type FROM GameObject WHERE ID="+idGameObject+")='BoostDamage', 'YES', 'NO')";
        String query3 =  "SELECT IF((SELECT Type FROM GameObject WHERE ID="+idGameObject+")='Food', 'YES', 'NO')";
        String query4 =  "SELECT IF((SELECT Type FROM GameObject WHERE ID="+idGameObject+")='Antenna', 'YES', 'NO')";

        String query = "SELECT * FROM GameObject ";

        MultiValueMap params = new MultiValueMap();
        params.put("ID", new Condition("=", String.valueOf(idGameObject)));

        try{
            session = FactorySession.openSession();
            result = session.customQuery(query1);
            if(result.equals("YES")){
                boostLife = (BoostLife) session.singleQuery(query, BoostLife.class, params);
                gameObject = boostLife;
            }
            result = session.customQuery(query2);
            if(result.equals("YES")){
                boostDamage = (BoostDamage) session.singleQuery(query, BoostDamage.class, params);
                gameObject = boostDamage;
            }
            result = session.customQuery(query3);
            if(result.equals("YES")){
                food = (Food) session.singleQuery(query, Food.class, params);
                gameObject = food;
            }
            result = session.customQuery(query4);
            if(result.equals("YES")){
                antenna = (Antenna) session.singleQuery(query, Antenna.class, params);
                gameObject = antenna;
            }
        }
        catch(Exception e){
            log.error("Error trying to open the session: " +e.getMessage());
            throw new GameObjectNotFoundException();
        }
        finally {
            session.close();
        }

        return gameObject;
    }

    //This method is called when a player grabs an object in the game
    @Override
    public void modifyAttributes(int idGameObject, int idUser, boolean buy) throws UserNotFoundException, GameObjectNotFoundException{
        Session session = null;
        GameObject object;
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

       object = getSingleObject(idGameObject);

        if(object != null) {

            if(buy == true) {
                player = object.modifyAttributesBuy(player);

                //Modify listobjects

                try {
                    session = FactorySession.openSession();
                    Players_Gameobjects players_gameobjects = new Players_Gameobjects(idUser, idGameObject);
                    session.customSave(players_gameobjects, false);
                } catch (Exception e) {
                    log.error("The user doesn't exist: " + e.getMessage());
                    throw new UserNotFoundException();
                } finally {
                    session.close();
                }
            }
            else{
                player = object.modifyAttributesSell(player);

                HashMap params = new HashMap();

                params.put("player_id", new Condition("=", String.valueOf(idUser)));
                params.put("gameObject_idGameObject", new Condition("=", String.valueOf(idGameObject)));
                //Modify listobjects

                try {
                    session = FactorySession.openSession();
                    Players_Gameobjects players_gameobjects = new Players_Gameobjects(idUser, idGameObject);
                    session.delete(players_gameobjects, params);
                } catch (Exception e) {
                    log.error("The user doesn't exist: " + e.getMessage());
                    throw new UserNotFoundException();
                } finally {
                    session.close();
                }
            }

            //Update database

            try {
                session = FactorySession.openSession();
                session.update(player, idUser, false);
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
            player.setPoints(points);
            session.update(player, idUser, false);
        }
        catch(Exception e){
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
            player.setEnemiesKilled(enemieskilled);
            session.update(player, idUser, false);
        }
        catch(Exception e){
            log.error("Error trying to open the session: " +e.getMessage());
            throw new UserNotFoundException();
        }
        finally {
            session.close();
        }
    }

    @Override
    public void sellObject(int idUser, int idGameObject, int points, int costObject) throws UserNotFoundException, GameObjectNotFoundException {
        points += costObject/2;
        updateUserPoints(idUser, points);
        modifyAttributes(idGameObject, idUser, false);
    }

    @Override
    public List<Stats> getStats(){
        Session session = null;
        List<Player> playerList = null;
        List<Stats> statsList = new LinkedList<>();
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
            stats.setLevel(playerList.get(i).getLevel());
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
       List<GameObject> antennaObjectsList = null;
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
           params.remove("type");
           params.put("type", new Condition("=", "'Antenna'"));
           antennaObjectsList = session.query(query, Antenna.class, params);
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
       gameObjectsList.addAll(antennaObjectsList);

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
            player.setCheckPoint(gameMapId);
            session.update(player, idUser, false);
        }
        catch(Exception e){
            log.error("Error trying to open the session: " +e.getMessage());
            throw new UserNotFoundException();
        }
        finally {
            session.close();
        }
    }
}
