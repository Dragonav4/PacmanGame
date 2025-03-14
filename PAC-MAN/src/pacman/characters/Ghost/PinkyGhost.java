package pacman.characters.Ghost;

public class PinkyGhost extends Ghost {
    public PinkyGhost(int x, int y, int direction) {
        super(x, y, direction);
    }

    @Override
    protected String getSpriteName() {
        return "pinky.png";
    }


}
