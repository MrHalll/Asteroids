package model.characters.factories;

import model.characters.Character;
import model.characters.Projectile;

public class ProjectileFactory extends CharacterFactory {
    @Override
    public Character createCharacter(int x, int y) {
        return new Projectile(x, y);
    }
}
