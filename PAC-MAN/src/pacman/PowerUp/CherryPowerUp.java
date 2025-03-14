package pacman.PowerUp;

import pacman.core.Game;

public class CherryPowerUp extends PowerUp {
    public CherryPowerUp(int x, int y) {
        super(x, y);
    }

    @Override
    protected String getSpriteName() {
        return "Cherry.png";
    }

    @Override
    public void apply(Game game) {
         game.score.increment(50);
    }
}

