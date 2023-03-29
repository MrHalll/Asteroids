package controller;

import model.Game;
import model.characters.Character;
import model.characters.EnemyShip;

import java.util.List;

public class Controller {
    private Game game;

    public void createGame(int width, int height){
        game = new Game(width, height);
    }
    public void startGame(){
        game.start();
    }

    public int getLevel() {
        return game.getLevel();
    }

    public int getNbrOfAsteroids() {
        return game.getNbrOfAsteroids();
    }

    public int getNbrOfEnemies() {
        return game.getNbrOfEnemies();
    }

    public void levelUp() {
        game.levelUp();
    }

    public void spawnObjects() {
        game.spawnObjects();
    }

    public int getPoints() {
        return game.getPoints();
    }

    public void addPoints(int points){
        game.addPoints(points);
    }

    public boolean isRunning(){
        return game.isRunning();
    }

    public int getHighscore(){
        return game.getHighscore();
    }

    public void stopGame(){
        game.stop();
    }

    public Character addAsteroid(){
        return game.addAsteroid();
    }

    public Character makePlayerShoot(){
        return game.makePlayerShoot();
    }

    public Character makeEnemyShipShoot(EnemyShip enemyShip) {
        return game.makeEnemyShipShoot(enemyShip);
    }

    public Character addEnemyShip() {
        return game.addEnemyShip();
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
}
