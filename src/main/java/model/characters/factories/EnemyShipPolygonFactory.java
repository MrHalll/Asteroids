package model.characters.factories;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public class EnemyShipPolygonFactory {
    private static EnemyShipPolygonFactory instance;
    private EnemyShipPolygonFactory(){}
    public Polygon createPolygon() {
        Polygon enemyShipPolygon = new Polygon();
        double c1 = Math.cos(Math.PI * 2 / 5);
        double c2 = Math.cos(Math.PI / 5);
        double s1 = Math.sin(Math.PI * 2 / 5);
        double s2 = Math.sin(Math.PI / 5);

        double size = 20.0;

        enemyShipPolygon.getPoints().addAll(
                size, 0.0,
                size * c1, -1 * size * s1,
                -1 * size * c2, -1 * size * s2,
                -1 * size * c2, size * s2,
                size * c1, size * s1
        );
        enemyShipPolygon.setFill(Color.RED);
        return enemyShipPolygon;
    }

    public static EnemyShipPolygonFactory getInstance() {
        if(instance == null) {
            instance = new EnemyShipPolygonFactory();
        }
        return instance;
    }
}
