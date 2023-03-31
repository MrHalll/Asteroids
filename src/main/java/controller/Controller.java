package controller;

import model.Game;
import model.Observable;
import model.Observer;
import model.characters.Character;
import model.characters.EnemyShip;

import java.util.ArrayList;
import java.util.List;

public class Controller extends Observable{
    private Game game;
    private static Controller instance;
    private List<Observer> observers = new ArrayList<>();
    private Controller(){}
    public void createGame(int width, int height){
        game = new Game(width, height);
        notifyObservers();
    }
    public void startGame(){
        game.start();
        notifyObservers();
    }
    public int getLevel() {
        return game.getLevel();
    }
    public void levelUp() {
        game.levelUp();
        notifyObservers();
    }
    public int getPoints() {
        return game.getPoints();
    }
    public void addPoints(int points){
        game.addPoints(points);
        notifyObservers();
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

    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer: observers) {
            observer.update(this);
        }
    }
}
