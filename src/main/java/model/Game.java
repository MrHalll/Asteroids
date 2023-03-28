package model;

import javafx.scene.paint.Color;
import model.characters.*;
import model.characters.Character;
import model.characters.Projectile;
import model.characters.Ship;
import model.characters.factories.AsteroidFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game {
    private int score = 0;
    private List<Character> asteroids;
    private List<Character> friendlyProjectiles;
    private List<Character> enemyProjectiles;
    private List<Character> enemyShips;
    private Ship ship;
    public int width = 0;
    public int height = 0;

    public Game(int width, int height){
        this.width = width;
        this.height = height;
    }

    public void start(){
        ship = new Ship(width / 2, height / 2);
        ship.getShape().setStroke(Color.WHITE);
        asteroids = new ArrayList<>();
        friendlyProjectiles = new ArrayList<>();
        enemyProjectiles = new ArrayList<>();
        enemyShips = new ArrayList<>();

        //skapar 5 asteroider
        for (int i = 0; i < 5; i++) {
            Random rnd = new Random();
            Asteroid asteroid = new Asteroid(rnd.nextInt(width / 3), rnd.nextInt(height));
            asteroid.getShape().setStroke(Color.WHITE);
            asteroids.add(asteroid);
        }
    }
    public void stop(){

    }

    }

    public Character makePlayerShoot(){
        Character projectile = getShip().shoot();
        friendlyProjectiles.add(projectile);
        projectile.accelerate();
        projectile.setMovement(projectile.getMovement().normalize().multiply(3));
        return projectile;
    }

    public Character makeEnemyShipShoot(EnemyShip enemyShip) {
        Character projectile = enemyShip.shoot(getShip());
        enemyProjectiles.add(projectile);
        projectile.accelerate();
        projectile.setMovement(projectile.getMovement().normalize().multiply(3));
        return projectile;
    }

    public void addScore(){
        score = score + 10;
    }

    public Character addAsteroid() {
        Character asteroid = new AsteroidFactory().createCharacter(width, height);
        asteroid.getShape().setStroke(Color.WHITE);
        asteroids.add(asteroid);
        return asteroid;
    }

    public Character addEnemyShip() {
        EnemyShip enemyShip = new EnemyShip(width, height);
        enemyShips.add(enemyShip);
        return enemyShip;
    }

    public Ship getShip() {
        return ship;
    }

    public List<Character> getAsteroids() {
        return asteroids;
    }

    public List<Character> getFriendlyProjectiles() {
        return friendlyProjectiles;
    }

    public List<Character> getEnemyProjectiles() {
        return enemyProjectiles;
    }

    public List<Character> getEnemyShips() {
        return enemyShips;
    }
}
