package model.characters.factories;

import model.characters.Character;

public abstract class CharacterFactory {
    public abstract Character createCharacter(int x, int y);
}
