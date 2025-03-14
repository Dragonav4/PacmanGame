package pacman.PowerUp;

import pacman.core.Game;

public class LivePowerUp extends PowerUp {

    public LivePowerUp(int x, int y) {
        super(x, y);
    }

    @Override
    protected String getSpriteName() {
        return "Apple.png";
    }

    @Override
    public void apply(Game game) {
        game.liveCounter.increment();
    }
}

