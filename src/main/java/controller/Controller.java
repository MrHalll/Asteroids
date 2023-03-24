package controller;

import model.Game;
import model.characters.Character;


import java.util.Collection;
import java.util.List;

public class Controller {
    private Game game;
    public Controller(int width, int height){
        game = new Game(width, height);
    }

    public void startGame(){
        game.start();
    }

    public void stopGame(){
        game.stop();
    }

    public Character addAsteroid(){
        return game.addAsteroid();
    }

    public Character addProjectile(){
        return game.addProjectile();
    }

    public Character getShip() {
        return game.getShip();
    }

    public List<Character> getAsteroids() {
        return game.getAsteroids();
    }

    public List<Character> getProjectiles() {
        return game.getProjectiles();
    }

    public int getPoints() {
        return game.getPoints();
    }

    public void addPoints(){
        game.addPoints();
    }

    public boolean isRunning(){
        return game.isRunning();
    }
}
