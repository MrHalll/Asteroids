package model.characters;

import javafx.scene.paint.Color;
import model.characters.factories.EnemyShipPolygonFactory;

import java.util.Random;


public class EnemyShip extends Character {

    private double rotationalMovement;

    public EnemyShip(int x, int y) {
        super(new EnemyShipPolygonFactory().createPolygon(), x, y);

        Random rnd = new Random();

        super.getShape().setRotate(rnd.nextInt(360));

        int accelerationAmount = 30;
        for (int i = 0; i < accelerationAmount; i++) {
            accelerate();
        }

        this.rotationalMovement = 5;

    }

    public Character shoot(Character target) {
        double dx = target.getX() - this.getX();
        double dy = target.getY() - this.getY();

        double angle = Math.toDegrees(Math.atan2(dy,dx));
        double offAngle = Math.random() * 20;

        Projectile projectile = new Projectile((int) this.getX(), (int) this.getY());
        projectile.getShape().setFill(Color.RED);
        projectile.getShape().setRotate(angle + offAngle);
        return projectile;
    }

    @Override
    public void move() {
        super.move();
        super.getShape().setRotate(super.getShape().getRotate() + rotationalMovement);
    }


}
