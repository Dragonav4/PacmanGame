package pacman.characters.Ghost;

public class BlinkyGhost extends Ghost {
    public BlinkyGhost(int x, int y, int direction) {
        super(x, y, direction);
    }

    @Override
    protected String getSpriteName() {
        return "blinky.png";
    }
}

