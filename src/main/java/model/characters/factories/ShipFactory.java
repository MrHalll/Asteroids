package model.characters.factories;

import model.characters.Character;
import model.characters.Ship;

public class ShipFactory extends CharacterFactory {
    @Override
    public Character createCharacter(int x, int y) {
        return new Ship(x, y);
    }
}
