package model.characters.factories;

import model.characters.Character;
import model.characters.Projectile;

public class ProjectileFactory extends CharacterFactory {
    private static ProjectileFactory instance;
    private ProjectileFactory(){}
    @Override
    public Character createCharacter(int x, int y) {
        return new Projectile(x, y);
    }

    public static ProjectileFactory getInstance() {
        if (instance == null) {
            instance = new ProjectileFactory();
        }
        return instance;
    }
}
