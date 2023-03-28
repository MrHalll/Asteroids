package model;

import javafx.scene.paint.Color;
import model.characters.*;
import model.characters.Character;
import model.characters.Ship;
import model.characters.factories.AsteroidFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game {
    private int points;
    private int highScore;
    private boolean isRunning;
    private List<Character> asteroids;
    private List<Character> friendlyProjectiles;
    private List<Character> enemyProjectiles;
    private List<Character> enemyShips;
    private Ship ship;
    public int width = 0;
    public int height = 0;
    private FileWriter fileWriter;
    private BufferedReader br;
    private String fileName = "src/main/resources/highscore.txt";

    public Game(int width, int height){
        this.width = width;
        this.height = height;
        this.highScore = getHighscore();
    }

    public void start(){
        isRunning = true;
        points = 0;
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
        isRunning = false;
        if (isHighScore()) {
            addNewHighScore();
        }
    }

    public int getHighscore(){
        try {
            br = new BufferedReader(new FileReader(fileName));
            String line = br.readLine();
            if (line != null) {
                highScore = Integer.parseInt(line);
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return highScore;
    }

    public boolean isHighScore() {
        return points > highScore;
    }

    public void addNewHighScore() {
        try {
            fileWriter = new FileWriter(fileName);
            fileWriter.write(String.valueOf(points));
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
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

    public int getPoints(){
        return points;
    }

    public void addPoints(){
        points = points + 1000;
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
    public boolean isRunning() {
        return isRunning;
    }
}
