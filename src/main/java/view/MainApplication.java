package view;

import controller.Controller;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.characters.Asteroid;
import model.characters.Character;
import model.characters.Projectile;
import model.characters.Ship;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class MainApplication extends Application {

    public static int WIDTH = 300;
    public static int HEIGHT = 200;
    private Controller controller;
    Map<KeyCode, Boolean> pressedKeys = new HashMap<>();

    public static int WIDTH = 500;
    public static int HEIGHT = 400;
    @Override
    public void start(Stage stage) throws IOException {
        Pane pane = new Pane();
        pane.setPrefSize(WIDTH, HEIGHT);
        Scene scene = new Scene(pane);
        stage.setTitle("Asteroids!");
        stage.setScene(scene);

        controller = new Controller();
        controller.startGame(WIDTH, HEIGHT);

        pane.getChildren().add(controller.getShip().getShape());

        for (Character asteroid: controller.getAsteroids()) {
            pane.getChildren().add(asteroid.getShape());
        }

        scene.setOnKeyPressed(event -> {
            pressedKeys.put(event.getCode(), Boolean.TRUE);
        });

        scene.setOnKeyReleased(event -> {
            pressedKeys.put(event.getCode(), Boolean.FALSE);
        });

        new AnimationTimer() {
            private long lastProjectileTime = 0;
            private long projectileDelay = 300_000_000;
            @Override
            public void handle(long now) {
                if (pressedKeys.getOrDefault(KeyCode.LEFT, false)) {
                    controller.getShip().turnLeft();
                }

                if (pressedKeys.getOrDefault(KeyCode.RIGHT, false)) {
                    controller.getShip().turnRight();
                }

                if (pressedKeys.getOrDefault(KeyCode.UP, false)) {
                    controller.getShip().accelerate();
                if(pressedKeys.getOrDefault(KeyCode.UP, false)) {
                    ship.accelerate();
                } else {
                    if(ship.getMovement().magnitude() > 0) {
                        ship.slowDown();
                    }
                }

                if (pressedKeys.getOrDefault(KeyCode.SPACE, false) && controller.getProjectiles().size() < 3) {

                if (pressedKeys.getOrDefault(KeyCode.SPACE, false) && projectiles.size() < 5  && now - lastProjectileTime > projectileDelay) {
                    // we shoot
                    pane.getChildren().add(controller.addProjectile().getShape());
                    Projectile projectile = new Projectile((int) ship.getCharacter().getTranslateX(), (int) ship.getCharacter().getTranslateY());
                    projectile.getCharacter().setRotate(ship.getCharacter().getRotate());
                    projectiles.add(projectile);

                    projectile.accelerate();
                    projectile.setMovement(projectile.getMovement().normalize().multiply(3));

                    pane.getChildren().add(projectile.getCharacter());

                    lastProjectileTime = now;
                }

                controller.getShip().move();
                controller.getAsteroids().forEach(asteroid -> asteroid.move());
                controller.getProjectiles().forEach(projectile -> projectile.move());

                controller.getAsteroids().forEach(asteroid -> {
                    if (controller.getShip().collide(asteroid)) {
                        controller.stopGame();
                        stop();
                    }
                });

                controller.getProjectiles().forEach(projectile -> {
                    controller.getAsteroids().forEach(asteroid -> {
                        if (projectile.collide(asteroid)) {
                            projectile.setAlive(false);
                            asteroid.setAlive(false);
                        }
                    });
                });

                controller.getProjectiles().stream()
                        .filter(projectile -> !projectile.isAlive())
                        .forEach(projectile -> pane.getChildren().remove(projectile.getShape()));
                controller.getProjectiles().removeAll(controller.getProjectiles().stream()
                        .filter(projectile -> !projectile.isAlive())
                        .collect(Collectors.toList()));

                controller.getAsteroids().stream()
                        .filter(asteroid -> !asteroid.isAlive())
                        .forEach(asteroid -> pane.getChildren().remove(asteroid.getShape()));
                controller.getAsteroids().removeAll(controller.getAsteroids().stream()
                        .filter(asteroid -> !asteroid.isAlive())
                        .collect(Collectors.toList()));

                if (Math.random() < 0.005) {
                    Character asteroid = controller.addAsteroid();
                    if (!asteroid.collide(controller.getShip())) {
                        pane.getChildren().add(asteroid.getShape());
                    }
                }
            }
        }.start();
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}