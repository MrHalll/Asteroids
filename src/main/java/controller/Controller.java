package controller;

import model.Game;
import model.characters.Character;
import model.characters.EnemyShip;

import java.util.List;

public class Controller {
    private Game game;
    private static Controller instance;
    private Controller(){}
    public void createGame(int width, int height){
        game = new Game(width, height);
    }
    public void startGame(){
        game.start();
    }
    public int getLevel() {
        return game.getLevel();
    }
    public void levelUp() {
        game.levelUp();
    }
    public int getPoints() {
        return game.getPoints();
    }
    public void addPoints(int points){
        game.addPoints(points);
    }
    public int getHighscore(){
        return game.getHighScore();
    }
    public void stopGame(){
        game.stop();
    }
    public Character makePlayerShoot(){
        return game.makePlayerShoot();
    }
    public Character makeEnemyShipShoot(EnemyShip enemyShip) {
        return game.makeEnemyShipShoot(enemyShip);
    }
    public Character getShip() {
        return game.getShip();
    }
    public List<Character> getAsteroids() {
        return game.getAsteroids();
    }
    public List<Character> getFriendlyProjectiles() {
        return game.getFriendlyProjectiles();
    }
    public List<Character> getEnemyProjectiles() {return game.getEnemyProjectiles();}
    public List<Character> getEnemyShips() {
        return game.getEnemyShips();
    }
    public static Controller getInstance(){
        if (instance == null)
            instance = new Controller();
        return instance;
    }
}
