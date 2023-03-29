package model.characters.factories;

import model.characters.Character;
import model.characters.EnemyShip;

public class EnemyShipFactory extends CharacterFactory {
    private static EnemyShipFactory instance;

    private EnemyShipFactory(){};
    @Override
    public Character createCharacter(int x, int y) {
        return new EnemyShip(x, y);
    }

    public static EnemyShipFactory getInstance() {
        if (instance == null) {
            instance = new EnemyShipFactory();
        }
        return instance;
    }
}
