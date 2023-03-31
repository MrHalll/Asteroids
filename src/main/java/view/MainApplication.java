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
import model.Observable;
import model.Observer;
import model.characters.Character;
import model.characters.EnemyShip;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class MainApplication extends Application implements Observer {
    private Controller controller;
    Map<KeyCode, Boolean> pressedKeys;
    public static int WIDTH = 800;
    public static int HEIGHT = 600;
    private int highscore;
    private int level;
    private int points;

    @Override
    public void start(Stage stage) throws IOException {
        controller = Controller.getInstance();
        controller.addObserver(this);
        controller.createGame(WIDTH, HEIGHT);
        Pane pane = new Pane();
        pane.setPrefSize(WIDTH, HEIGHT);
        Scene startScene = new Scene(pane);
        startScene.setFill(Color.BLACK);
        stage.setTitle("Asteroids!");
        stage.setScene(startScene);

        Text instruction = new Text((double) (WIDTH - 565), (double) (HEIGHT / 2), "PRESS SPACE TO PLAY");
        Text highscoreLabelStart = new Text(WIDTH - 180, 20, "HIGH SCORE: " + highscore);
        instruction.setFill(Color.WHITE);
        highscoreLabelStart.setFill(Color.WHITE);
        instruction.setFont(Font.font("courier new", FontWeight.BOLD, FontPosture.REGULAR, 30));
        highscoreLabelStart.setFont(Font.font("courier new", FontWeight.BOLD, FontPosture.REGULAR, 15));
        pane.getChildren().add(instruction);
        pane.getChildren().add(highscoreLabelStart);

        startScene.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.SPACE) {
                Pane playingPane = new Pane();
                playingPane.setPrefSize(WIDTH, HEIGHT);
                Scene playingScene = new Scene(playingPane);
                playingScene.setFill(Color.BLACK);
                stage.setScene(playingScene);
                controller.createGame(WIDTH, HEIGHT);
                controller.startGame();
                pressedKeys =  new HashMap<>();
                Text pointLabel = new Text(10, 20, "POINTS: " + points);
                Text levelLabel = new Text(pointLabel.getX(), pointLabel.getY() + 25, "LEVEL " + level);
                Text highscoreLabel = new Text(WIDTH - 180, 20, "HIGH SCORE: " + highscore);
                pointLabel.setFill(Color.WHITE);
                levelLabel.setFill(Color.WHITE);
                highscoreLabel.setFill(Color.WHITE);
                pointLabel.setFont(Font.font("courier new", FontWeight.BOLD, FontPosture.REGULAR, 15));
                levelLabel.setFont(Font.font("courier new", FontWeight.BOLD, FontPosture.REGULAR, 15));
                highscoreLabel.setFont(Font.font("courier new", FontWeight.BOLD, FontPosture.REGULAR, 15));
                playingPane.getChildren().add(pointLabel);
                playingPane.getChildren().add(levelLabel);
                playingPane.getChildren().add(highscoreLabel);
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
                    private long projectileDelay = 200_000_000;
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
                                highscoreLabelStart.setText("HIGH SCORE: " + highscore);
                            }
                        });

                        controller.getAsteroids().forEach(asteroid -> {
                            if (controller.getShip().collide(asteroid)) {
                                controller.stopGame();
                                stop();
                                stage.setScene(startScene);
                                highscoreLabelStart.setText("HIGH SCORE: " + highscore);
                            }
                        });

                        controller.getFriendlyProjectiles().forEach(projectile -> {
                            controller.getAsteroids().forEach(asteroid -> {
                                if (projectile.collide(asteroid)) {
                                    projectile.setAlive(false);
                                    asteroid.setAlive(false);
                                    controller.addPoints(1000);
                                    pointLabel.setText("POINTS: " + points);
                                }
                            });
                            controller.getEnemyShips().forEach(enemyShip -> {
                                if (projectile.collide(enemyShip)) {
                                    projectile.setAlive(false);
                                    enemyShip.setAlive(false);
                                    controller.addPoints(2000);
                                    pointLabel.setText("POINTS: " + points);
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
                    }
                }.start();
            }
        });
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void update(Observable observable) {
        points = controller.getPoints();
        level = controller.getLevel();
        highscore = controller.getHighscore();
    }
}