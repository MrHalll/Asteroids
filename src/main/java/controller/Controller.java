package controller;

import model.Game;
import model.characters.Character;
import model.characters.EnemyShip;

import java.util.List;

public class Controller {
    private Game game;

    public void startGame(int width, int height){
        game = new Game(width, height);
        game.start();
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
