package model.characters.factories;

import model.characters.Asteroid;
import model.characters.Character;

public class AsteroidFactory extends CharacterFactory {
    @Override
    public Character createCharacter(int x, int y) {
        return new Asteroid(x, y);
    }
}
