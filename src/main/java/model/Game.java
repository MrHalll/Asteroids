package model;

import javafx.scene.paint.Color;
import model.characters.Asteroid;
import model.characters.Character;
import model.characters.Projectile;
import model.characters.Ship;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game {
    private int score = 0;
    private List<Character> asteroids;
    private List<Character> projectiles;
    private Character enemyShip;
    private Character ship;
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
        projectiles = new ArrayList<>();

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
    public void reset(){

    }

    public Character addProjectile(){
        Projectile projectile = new Projectile((int) ship.getX(), (int) ship.getY());
        projectile.getShape().setFill(Color.WHITE);
        projectile.getShape().setRotate(ship.getShape().getRotate());
        projectiles.add(projectile);
        projectile.accelerate();
        projectile.setMovement(projectile.getMovement().normalize().multiply(3));
        return projectile;
    }

    public void addScore(){
        score = score + 10;
    }

    public Character addAsteroid() {
        Asteroid asteroid = new Asteroid(width, height);
        asteroid.getShape().setStroke(Color.WHITE);
        asteroids.add(asteroid);
        return asteroid;
    }

    public Character getShip() {
        return ship;
    }

    public List<Character> getAsteroids() {
        return asteroids;
    }

    public List<Character> getProjectiles() {
        return projectiles;
    }
}
