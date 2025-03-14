package pacman.characters.Ghost;

public class inkyGhost extends Ghost {
    public inkyGhost(int x, int y, int direction) {
        super(x, y, direction);
    }

    @Override
    protected String getSpriteName() {
        return "inky.png";
    }
}
