package model;

import javafx.beans.InvalidationListener;
import javafx.scene.paint.Color;
import model.characters.*;
import model.characters.Character;
import model.characters.Ship;
import model.characters.factories.AsteroidFactory;
import model.characters.factories.EnemyShipFactory;
import model.characters.factories.ShipFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game {
    private int level;
    private int nbrOfAsteroids;
    private int nbrOfEnemies;
    private int points;
    private int highScore;
    private boolean isRunning;
    private List<Character> asteroids;
    private List<Character> friendlyProjectiles;
    private List<Character> enemyProjectiles;
    private List<Character> enemyShips;
    private Character ship;
    public int width = 0;
    public int height = 0;
    private FileWriter fileWriter;
    private BufferedReader br;
    private String fileName = "src/main/resources/highscore.txt";

    public Game(int width, int height){
        this.width = width;
        this.height = height;
    }

    public void start(){
        level = 1;
        nbrOfAsteroids = 5;
        nbrOfEnemies = 0;
        isRunning = true;
        points = 0;
        highScore = getHighScore();
        ship = ShipFactory.getInstance().createCharacter(width / 2, height / 2);
        ship.getShape().setStroke(Color.WHITE);
        asteroids = new ArrayList<>();
        friendlyProjectiles = new ArrayList<>();
        enemyProjectiles = new ArrayList<>();
        enemyShips = new ArrayList<>();
        spawnObjects();
    }
    public void stop(){
        isRunning = false;
        if (isHighScore()) {
            addNewHighScore();
        }
    }

    public void levelUp() {
        level++;
        nbrOfAsteroids += 3;
        nbrOfEnemies += 1;
        spawnObjects();
    }

    public void spawnObjects() {
        for (int i = 0; i < nbrOfAsteroids; i++) {
            addAsteroid();
        }

        for (int i = 0; i < nbrOfEnemies; i++) {
            addEnemyShip();
        }
    }

    public int getLevel() {
        return level;
    }

    public int getHighScore(){
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

    public void addPoints(int pointsToAdd){
        points += pointsToAdd;
    }

    public Character addAsteroid() {
        Random rnd = new Random();
        Character asteroid;
        do {
            asteroid = AsteroidFactory.getInstance().createCharacter(rnd.nextInt(width), rnd.nextInt(height));
        } while (asteroid.collide(getShip()) || (asteroid.getDistance(getShip()) < 100));

        asteroid.getShape().setStroke(Color.WHITE);
        asteroids.add(asteroid);
        return asteroid;
    }

    public Character addEnemyShip() {
        Random rnd = new Random();
        Character enemyShip;

        do {
            enemyShip = EnemyShipFactory.getInstance().createCharacter(rnd.nextInt(width), rnd.nextInt(height));
        } while (enemyShip.collide(getShip()) || (enemyShip.getDistance(getShip()) < 100));

        enemyShip.getShape().setFill(Color.RED);
        enemyShips.add(enemyShip);
        return enemyShip;
    }

    public Ship getShip() {
        return (Ship) ship;
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
