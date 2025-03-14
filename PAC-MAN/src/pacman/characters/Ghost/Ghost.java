package pacman.characters.Ghost;

import Utils.FileUtils;
import Utils.Speed;
import pacman.characters.CharacterPacman;
import pacman.core.CoordinateConverter;
import pacman.core.Maze;

import java.awt.*;
import java.awt.image.ImageObserver;

public abstract class Ghost extends Speed {
    public boolean huntMode;
    private int x, y, initialX, initialY, dx, dy, direction, turnCount;
    private static final int[] dxOptions = {-1, 0, 1, 0};
    private static final int[] dyOptions = {0, -1, 0, 1};

    private boolean isWandering;

    private Image ghost;
    private static Image scaredGhost;
    public Ghost(int x, int y, int direction) {
        this.initialX = x;
        this.initialY = y;
        this.x = CoordinateConverter.toScreenCoordinate(x);
        this.y = CoordinateConverter.toScreenCoordinate(y);
        setDirection(direction % dxOptions.length);
        scaredGhost = FileUtils.loadImage("/pacman/images/sprites/Ghosts/scaredGhost.png");
        ghost = FileUtils.loadImage("/pacman/images/sprites/Ghosts/" +getSpriteName());
    }

    public void reset() {
        this.x = CoordinateConverter.toScreenCoordinate(initialX);
        this.y = CoordinateConverter.toScreenCoordinate(initialY);
    }

    protected abstract String getSpriteName();

    public void drawGhost(Graphics2D g2d, ImageObserver imageObserver) {
        g2d.drawImage(huntMode ? scaredGhost : ghost, x, y, 20, 20, imageObserver);
    }

    private int getNewCoordinate(int coordinate, int delta) {
        return coordinate + delta * getSpeed();//6;
    }

    private void setRandomDirection(int oldDirection, int openedCells) {
        var oppositeDirection = (oldDirection+2)%dxOptions.length;
        var newDirection = oppositeDirection;
        while(true) {
            newDirection = (int)(Math.random() * dxOptions.length);
            if (openedCells<=1 || newDirection != oppositeDirection)
                break;
        }
        setDirection(newDirection);
    }

    private void setDirection(int direction) {
        this.direction = direction;
        dx = dxOptions[direction];
        dy = dyOptions[direction];
        turnCount = (int) (Math.random() * 15) + 3;
    }

    public void moveGhost(Maze maze) {
        if (x % CoordinateConverter.block_size == 0 &&
            y % CoordinateConverter.block_size == 0) {
            var openedCells = maze.getOpenedCells(x, y);

            var oldDirection = direction;
                do {
                    setRandomDirection(oldDirection,openedCells);
                } while (maze.hasWall(
                        x,
                        y,
                        dx,
                        dy));
        }
        x = mod(getNewCoordinate(x, dx), CoordinateConverter.toScreenCoordinate(maze.getWidth()));
        y = getNewCoordinate(y, dy);
    }

    private int mod(int value, int mod) {
        while(value<0) {
            value+=mod;
        }
        return value%mod;
    }


    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isWandering() {
        return isWandering;
    }


    public boolean meet(CharacterPacman pacman) {
        var ghostRectangle = new Rectangle(x, y, CoordinateConverter.block_size, CoordinateConverter.block_size);
        var pacmanRectangle = new Rectangle(pacman.getX(), pacman.getY(), CoordinateConverter.block_size, CoordinateConverter.block_size);
        return ghostRectangle.intersects(pacmanRectangle);
    }
}

