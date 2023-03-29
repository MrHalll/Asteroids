package model.characters.factories;

import model.characters.Asteroid;
import model.characters.Character;

public class AsteroidFactory extends CharacterFactory {
    private static AsteroidFactory instance;
    private AsteroidFactory() {}
    @Override
    public Character createCharacter(int x, int y) {
        return new Asteroid(x, y);
    }

    public static AsteroidFactory getInstance() {
        if(instance == null) {
            instance = new AsteroidFactory();
        }
        return instance;
    }
}
