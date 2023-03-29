package model.characters.factories;

import model.characters.Character;
import model.characters.Ship;

public class ShipFactory extends CharacterFactory {
    private static ShipFactory instance;
    private ShipFactory(){}
    @Override
    public Character createCharacter(int x, int y) {
        return new Ship(x, y);
    }

    public static ShipFactory getInstance() {
        if(instance == null) {
            instance = new ShipFactory();
        }
        return instance;
    }
}
