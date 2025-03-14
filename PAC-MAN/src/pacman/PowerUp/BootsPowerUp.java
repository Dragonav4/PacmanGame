package pacman.PowerUp;

import pacman.core.Game;

import pacman.characters.CharacterPacman;

public class BootsPowerUp extends PowerUp {
    public BootsPowerUp(int x, int y) {
        super(x, y);
    }

    @Override
    protected String getSpriteName() {
        return "Boots.png";
    }

    @Override
    public void apply(Game game) {
        game.characterPacman.temporarilyIncreasedSpeed(10, CharacterPacman.defaultSpeed);
    }
}
