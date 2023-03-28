package model.characters;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public class Ship extends Character {

    public Ship(int x, int y) {
        super(new Polygon(-5, -5, 10, 0, -5, 5), x, y);
    }

    public Character shoot() {
        Projectile projectile = new Projectile((int) this.getX(), (int) this.getY());
        projectile.getShape().setFill(Color.WHITE);
        projectile.getShape().setRotate(this.getShape().getRotate());
        return projectile;
    }

}
