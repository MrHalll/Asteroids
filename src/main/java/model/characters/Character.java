package model.characters;

import javafx.geometry.Point2D;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;
import view.MainApplication;

public abstract class Character {
    private Polygon shape;
    private Point2D movement;
    private boolean alive = true;

    public Character(Polygon polygon, int x, int y) {
        this.shape = polygon;
        this.shape.setTranslateX(x);
        this.shape.setTranslateY(y);
        this.movement = new Point2D(0, 0);
    }

    public Polygon getShape() {
        return shape;
    }

    public void turnLeft() {
        this.shape.setRotate(this.shape.getRotate() - 5);
    }

    public void turnRight() {
        this.shape.setRotate(this.shape.getRotate() + 5);
    }

    public void move() {
        this.shape.setTranslateX(this.shape.getTranslateX() + this.movement.getX());
        this.shape.setTranslateY(this.shape.getTranslateY() + this.movement.getY());

        if (this.shape.getTranslateX() < 0) {
            this.shape.setTranslateX(this.shape.getTranslateX() + MainApplication.WIDTH);
        }

        if (this.shape.getTranslateX() > MainApplication.WIDTH) {
            this.shape.setTranslateX(this.shape.getTranslateX() % MainApplication.WIDTH);
        }

        if (this.shape.getTranslateY() < 0) {
            this.shape.setTranslateY(this.shape.getTranslateY() + MainApplication.HEIGHT);
        }

        if (this.shape.getTranslateY() > MainApplication.HEIGHT) {
            this.shape.setTranslateY(this.shape.getTranslateY() % MainApplication.HEIGHT);
        }
    }

    public void accelerate() {
        double changeX = Math.cos(Math.toRadians(this.shape.getRotate()));
        double changeY = Math.sin(Math.toRadians(this.shape.getRotate()));

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
        Shape collisionArea = Shape.intersect(this.shape, other.getShape());
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

    public double getX() {
        return shape.getTranslateX();
    }

    public double getY() {
        return shape.getTranslateY();
    }
}
