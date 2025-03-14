package pacman.characters.Ghost;

public class ClydeGhost extends Ghost {
    public ClydeGhost(int x, int y, int direction) {
        super(x, y, direction);
    }

    @Override
    protected String getSpriteName() {
        return "clyde.png";
    }
}
