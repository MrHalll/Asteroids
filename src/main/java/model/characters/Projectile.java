package model.characters;

import javafx.scene.shape.Polygon;
import view.MainApplication;

public class Projectile extends Character {

    public Projectile(int x, int y) {
        super(new Polygon(2, -2, 2, 2, -2, 2, -2, -2), x, y);
    }

    @Override
    public void move() {
        this.getShape().setTranslateX(this.getShape().getTranslateX() + this.getMovement().getX());
        this.getShape().setTranslateY(this.getShape().getTranslateY() + this.getMovement().getY());
        if (this.getShape().getTranslateX() < 0 || this.getShape().getTranslateX() > MainApplication.WIDTH
        || this.getShape().getTranslateY() < 0 || this.getShape().getTranslateY() > MainApplication.HEIGHT) {
            this.setAlive(false);
        }
    }
}
