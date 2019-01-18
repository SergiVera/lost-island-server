package dsa.eetac.upc.edu;

public class UserStats {
    int currentHealth, maxHealth,attack,checkPoint,points,enemiesKilled,level,user_id;
    public UserStats(){}

    public int getCurrentHealth() {
        return currentHealth;
    }

    public void setCurrentHealth(int currentHealth) {
        this.currentHealth = currentHealth;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getCheckPoint() {
        return checkPoint;
    }

    public void setCheckPoint(int checkPoint) {
        this.checkPoint = checkPoint;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getEnemiesKilled() {
        return enemiesKilled;
    }

    public void setEnemiesKilled(int enemiesKilled) {
        this.enemiesKilled = enemiesKilled;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    @Override
    public String toString() {
        return "UserStats{" +
                "currentHealth=" + currentHealth +
                ", maxHealth=" + maxHealth +
                ", attack=" + attack +
                ", checkPoint=" + checkPoint +
                ", points=" + points +
                ", enemiesKilled=" + enemiesKilled +
                ", level=" + level +
                ", user_id=" + user_id +
                '}';
    }
}
