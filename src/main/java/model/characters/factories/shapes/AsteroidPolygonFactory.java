package model.characters.factories.shapes;

import javafx.scene.shape.Polygon;

import java.util.Random;

public class AsteroidPolygonFactory {
    private static AsteroidPolygonFactory instance;
    private AsteroidPolygonFactory() {}
    public Polygon createPolygon() {
        Random rnd = new Random();
        int nrOfVertices = rnd.nextInt(6) + 5;
        double size = 10 + rnd.nextInt(10);
        Polygon polygon = new Polygon();

        for (int i = 0; i < nrOfVertices; i++) {
            double angle = 2 * Math.PI * i / nrOfVertices;
            double x = size * Math.cos(angle);
            double y = size * Math.sin(angle);
            polygon.getPoints().addAll(x, y);
        }

        for (int i = 0; i < polygon.getPoints().size(); i++) {
            polygon.getPoints().set(i, polygon.getPoints().get(i) + rnd.nextInt(10) - 5);
        }

        return polygon;
    }

    public static AsteroidPolygonFactory getInstance() {
        if (instance == null) {
            instance = new AsteroidPolygonFactory();
        }
        return instance;
    }
}
