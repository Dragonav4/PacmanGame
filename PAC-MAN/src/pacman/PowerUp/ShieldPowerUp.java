package pacman.PowerUp;

import pacman.core.Game;

public class ShieldPowerUp extends PowerUp {
    
    public ShieldPowerUp(int x, int y) {
        super(x,y);
    }

    @Override
    protected String getSpriteName() {
        return "Shield.png";
    }

    @Override
    public void apply(Game game) {
        game.characterPacman.isInvulnerable = true;
    }
}

