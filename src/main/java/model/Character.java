package model;

import javafx.geometry.Point2D;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;
import view.MainApplication;

public abstract class Character {
    private Polygon getCharacter;
    private Point2D movement;

    private boolean alive = true;

    public Character(Polygon polygon, int x, int y) {
        this.getCharacter = polygon;
        this.getCharacter.setTranslateX(x);
        this.getCharacter.setTranslateY(y);

        this.movement = new Point2D(0, 0);
    }

    public Polygon getCharacter() {
        return getCharacter;
    }

    public void turnLeft() {
        this.getCharacter.setRotate(this.getCharacter.getRotate() - 5);
    }

    public void turnRight() {
        this.getCharacter.setRotate(this.getCharacter.getRotate() + 5);
    }

    public void move() {
        this.getCharacter.setTranslateX(this.getCharacter.getTranslateX() + this.movement.getX());
        this.getCharacter.setTranslateY(this.getCharacter.getTranslateY() + this.movement.getY());

        if (this.getCharacter.getTranslateX() < 0) {
            this.getCharacter.setTranslateX(this.getCharacter.getTranslateX() + MainApplication.WIDTH);
        }

        if (this.getCharacter.getTranslateX() > MainApplication.WIDTH) {
            this.getCharacter.setTranslateX(this.getCharacter.getTranslateX() % MainApplication.WIDTH);
        }

        if (this.getCharacter.getTranslateY() < 0) {
            this.getCharacter.setTranslateY(this.getCharacter.getTranslateY() + MainApplication.HEIGHT);
        }

        if (this.getCharacter.getTranslateY() > MainApplication.HEIGHT) {
            this.getCharacter.setTranslateY(this.getCharacter.getTranslateY() % MainApplication.HEIGHT);
        }
    }

    public void accelerate() {
        double changeX = Math.cos(Math.toRadians(this.getCharacter.getRotate()));
        double changeY = Math.sin(Math.toRadians(this.getCharacter.getRotate()));

        changeX *= 0.05;
        changeY *= 0.05;

        double currentSpeed = this.movement.add(changeX, changeY).magnitude();
        double topSpeed = 4.0;

        if (currentSpeed < topSpeed) {
            this.movement = this.movement.add(changeX, changeY);
        }


    }

    public void slowDown() {
        double deceleration = 0.04; // adjust this value as needed
        double currentSpeed = this.movement.magnitude();

        if (currentSpeed > deceleration) {
            double newSpeed = currentSpeed - deceleration;
            Point2D newMovement = this.movement.normalize().multiply(newSpeed);

            // prevent going backwards
            if (newMovement.dotProduct(this.movement) < 0) {
                newMovement = new Point2D(0, 0);
            }

            this.movement = newMovement;
        } else {
            // stop the ship
            this.movement = new Point2D(0, 0);
        }
    }

    public boolean collide(Character other) {
        Shape collisionArea = Shape.intersect(this.getCharacter, other.getCharacter());
        return collisionArea.getBoundsInLocal().getWidth() != -1;
    }

    public Point2D getMovement() {
        return movement;
    }

    public void setMovement(Point2D movement) {
        this.movement = movement;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public boolean isAlive() {
        return alive;
    }
}
