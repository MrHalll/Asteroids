package model.characters.factories;

import model.characters.Character;
import model.characters.EnemyShip;

public class EnemyShipFactory extends CharacterFactory {
    @Override
    public Character createCharacter(int x, int y) {
        return new EnemyShip(x, y);
    }
}
