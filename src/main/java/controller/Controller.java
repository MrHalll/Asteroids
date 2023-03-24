package controller;

import model.Game;
import model.characters.Asteroid;
import model.characters.Character;
import model.characters.Projectile;
import model.characters.Ship;
import view.MainApplication;

import java.util.Collection;
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
}
