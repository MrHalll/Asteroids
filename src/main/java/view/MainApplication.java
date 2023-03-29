package view;

import controller.Controller;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.characters.Character;
import model.characters.EnemyShip;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class MainApplication extends Application {
    private Controller controller;
    Map<KeyCode, Boolean> pressedKeys;
    public static int WIDTH = 800;
    public static int HEIGHT = 600;

    @Override
    public void start(Stage stage) throws IOException {
        controller = new Controller(WIDTH, HEIGHT);
        Pane pane = new Pane();
        pane.setPrefSize(WIDTH, HEIGHT);
        Scene startScene = new Scene(pane);
        startScene.setFill(Color.BLACK);
        stage.setTitle("Asteroids!");
        stage.setScene(startScene);

        Text instruction = new Text((double) (200), (double) (300), "PRESS SPACE TO PLAY");
        instruction.setFill(Color.WHITE);
        instruction.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 30));
        pane.getChildren().add(instruction);

        startScene.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.SPACE) {
                Pane playingPane = new Pane();
                playingPane.setPrefSize(WIDTH, HEIGHT);
                Scene playingScene = new Scene(playingPane);
                playingScene.setFill(Color.BLACK);
                stage.setScene(playingScene);
                controller = new Controller(WIDTH, HEIGHT);
                pressedKeys =  new HashMap<>();
                controller.startGame();
                Text pointLabel = new Text(10, 20, "POINTS: " + controller.getPoints());
                Text highscoreLabel = new Text(WIDTH - 110, 20, "HIGH SCORE: " + controller.getHighscore());
                Text levelLabel = new Text((WIDTH / 2)-20, 20, "LEVEL " + controller.getLevel());
                pointLabel.setFill(Color.WHITE);
                highscoreLabel.setFill(Color.WHITE);
                levelLabel.setFill(Color.WHITE);
                playingPane.getChildren().add(pointLabel);
                playingPane.getChildren().add(highscoreLabel);
                playingPane.getChildren().add(levelLabel);
                playingPane.getChildren().add(controller.getShip().getShape());

                for (Character asteroid : controller.getAsteroids()) {
                    playingPane.getChildren().add(asteroid.getShape());
                }

                for (Character enemyShip : controller.getEnemyShips()) {
                    playingPane.getChildren().add(enemyShip.getShape());
                }

                playingScene.setOnKeyPressed(event -> {
                    pressedKeys.put(event.getCode(), Boolean.TRUE);
                });

                playingScene.setOnKeyReleased(event -> {
                    pressedKeys.put(event.getCode(), Boolean.FALSE);
                });

                new AnimationTimer() {
                    private long lastProjectileTime = 0;
                    private long projectileDelay = 300_000_000;

                    private long lastEnemyProjectileTime = 0;
                    private long enemyProjectileDelay = 4_000_000_000L;

                    @Override
                    public void handle(long now) {

                        //User Inputs
                        if (pressedKeys.getOrDefault(KeyCode.LEFT, false)) {
                            controller.getShip().turnLeft();
                        }

                        if (pressedKeys.getOrDefault(KeyCode.RIGHT, false)) {
                            controller.getShip().turnRight();
                        }

                        if (pressedKeys.getOrDefault(KeyCode.UP, false)) {
                            controller.getShip().accelerate();
                        } else {
                            if (controller.getShip().getMovement().magnitude() > 0) {
                                controller.getShip().slowDown();
                            }
                        }

                        if (pressedKeys.getOrDefault(KeyCode.SPACE, false) && controller.getFriendlyProjectiles().size() < 5 && now - lastProjectileTime > projectileDelay) {
                            // we shoot
                            playingPane.getChildren().add(controller.makePlayerShoot().getShape());
                            lastProjectileTime = now;
                        }
                        //-----

                        //Move objects
                        controller.getShip().move();
                        controller.getAsteroids().forEach(asteroid -> asteroid.move());
                        controller.getFriendlyProjectiles().forEach(projectile -> projectile.move());
                        controller.getEnemyProjectiles().forEach(projectile -> projectile.move());
                        controller.getEnemyShips().forEach(enemyShip -> enemyShip.move());
                        //-----

                        //Handle collisions and shooting
                        if (controller.getEnemyProjectiles().size() < 10 && now - lastEnemyProjectileTime > enemyProjectileDelay) {
                            controller.getEnemyShips().forEach(enemyShip -> {
                                playingPane.getChildren().add(controller.makeEnemyShipShoot((EnemyShip) enemyShip).getShape());
                            });
                            lastEnemyProjectileTime = now;
                        }

                        controller.getEnemyShips().forEach(enemyShip -> {
                            if (controller.getShip().collide(enemyShip)) {
                                controller.stopGame();
                                stop();
                                stage.setScene(startScene);
                            }
                        });

                        controller.getEnemyProjectiles().forEach(enemyProjectile -> {
                            if (controller.getShip().collide(enemyProjectile)) {
                                controller.stopGame();
                                stop();
                                stage.setScene(startScene);
                            }
                        });

                        controller.getAsteroids().forEach(asteroid -> {
                            if (controller.getShip().collide(asteroid)) {
                                controller.stopGame();
                                stop();
                                stage.setScene(startScene);
                            }
                        });

                        controller.getFriendlyProjectiles().forEach(projectile -> {
                            controller.getAsteroids().forEach(asteroid -> {
                                if (projectile.collide(asteroid)) {
                                    projectile.setAlive(false);
                                    asteroid.setAlive(false);
                                    controller.addPoints();
                                    pointLabel.setText("POINTS: " + controller.getPoints());
                                }
                            });
                            controller.getEnemyShips().forEach(enemyShip -> {
                                if (projectile.collide(enemyShip)) {
                                    projectile.setAlive(false);
                                    enemyShip.setAlive(false);
                                    controller.addPoints();
                                    pointLabel.setText("POINTS: " + controller.getPoints());
                                }
                            });
                        });

                        //-----

                        //Check if objects are alive
                        controller.getFriendlyProjectiles().stream()
                                .filter(projectile -> !projectile.isAlive())
                                .forEach(projectile -> playingPane.getChildren().remove(projectile.getShape()));
                        controller.getFriendlyProjectiles().removeAll(controller.getFriendlyProjectiles().stream()
                                .filter(projectile -> !projectile.isAlive())
                                .collect(Collectors.toList()));

                        controller.getEnemyProjectiles().stream()
                                .filter(projectile -> !projectile.isAlive())
                                .forEach(projectile -> playingPane.getChildren().remove(projectile.getShape()));
                        controller.getEnemyProjectiles().removeAll(controller.getEnemyProjectiles().stream()
                                .filter(projectile -> !projectile.isAlive())
                                .collect(Collectors.toList()));

                        controller.getAsteroids().stream()
                                .filter(asteroid -> !asteroid.isAlive())
                                .forEach(asteroid -> playingPane.getChildren().remove(asteroid.getShape()));
                        controller.getAsteroids().removeAll(controller.getAsteroids().stream()
                                .filter(asteroid -> !asteroid.isAlive())
                                .collect(Collectors.toList()));

                        controller.getEnemyShips().stream()
                                .filter(enemyShip -> !enemyShip.isAlive())
                                .forEach(enemyShip -> playingPane.getChildren().remove(enemyShip.getShape()));
                        controller.getEnemyShips().removeAll(controller.getEnemyShips().stream()
                                .filter(enemyShip -> !enemyShip.isAlive())
                                .collect(Collectors.toList()));

                        if (controller.getAsteroids().size() == 0) {

                            if (controller.getEnemyShips().size() > 0) {
                                controller.getEnemyShips().forEach(enemyShip -> {
                                    playingPane.getChildren().remove(enemyShip.getShape());
                                });
                                controller.getEnemyShips().clear();
                            }
                            if (controller.getEnemyProjectiles().size() > 0) {
                                controller.getEnemyProjectiles().forEach(enemyProjectile -> {
                                    playingPane.getChildren().remove(enemyProjectile.getShape());
                                });
                                controller.getEnemyProjectiles().clear();
                            }
                            if (controller.getFriendlyProjectiles().size() > 0) {
                                controller.getFriendlyProjectiles().forEach(friendlyProjectile -> {
                                    playingPane.getChildren().remove(friendlyProjectile.getShape());
                                });
                                controller.getFriendlyProjectiles().clear();
                            }

                            controller.levelUp();
                            levelLabel.setText("LEVEL " + controller.getLevel());
                            controller.getAsteroids().forEach(asteroid -> {
                                playingPane.getChildren().add(asteroid.getShape());
                            });

                            controller.getEnemyShips().forEach(enemyShip -> {
                                if (!enemyShip.collide(controller.getShip())) {
                                    playingPane.getChildren().add(enemyShip.getShape());
                                }
                            });


                        }

                        //Spawn game objects
                        /*if (Math.random() < 0.005) {
                            Character asteroid = controller.addAsteroid();
                            if (!asteroid.collide(controller.getShip())) {
                                playingPane.getChildren().add(asteroid.getShape());
                            }
                        }

                        if (now - lastEnemyShipSpawn > enemyShipSpawnDelay) {
                            Character enemyShip = controller.addEnemyShip();
                            if (!enemyShip.collide(controller.getShip())) {
                                playingPane.getChildren().add(enemyShip.getShape());
                                lastEnemyShipSpawn = now;
                            }
                        }*/
                        //-----
                    }
                }.start();
            }
        });
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}