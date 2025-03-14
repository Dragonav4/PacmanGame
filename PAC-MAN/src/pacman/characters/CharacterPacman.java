package pacman.characters;

import Utils.FileUtils;
import Utils.Speed;
import pacman.core.CoordinateConverter;
import pacman.core.Maze;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class CharacterPacman extends Speed {
    private int pacman_x;
    private int pacman_y;
    private int pacman_dx;
    private int pacman_dy;
    private int request_dx, request_dy;
    private ArrayList<Image> up, down, left, right;
    private int currentFrame;
    private long lastFrameChange;
    public boolean isInvulnerable = false;
    private static final int FRAME_INTERVAL = 50;
    public static final int defaultSpeed = 3;
    public CharacterPacman(int x, int y) {
        loadImages();
        initialize(x,y);

    }


    private void loadImages() {
        up = loadImages("up");
        down = loadImages("down");
        left=loadImages("left");
        right=loadImages("right");
    }

    private ArrayList<Image> loadImages(String name){
        var result = new ArrayList<Image>();
        for (int i = 0; i < 4; i++) {
            result.add(FileUtils.loadImage("/pacman/images/sprites/pacman/"+name+i+".png"));
        }
        return result;
    }


    public void initialize(int x, int y) {
        speed = defaultSpeed;
        pacman_x = CoordinateConverter.toScreenCoordinate(x);
        pacman_y = CoordinateConverter.toScreenCoordinate(y);
        pacman_dx = 0;
        pacman_dy = 0;
        request_dy = 0;
        request_dx = 0;
        currentFrame = 0; // current animation frame
        lastFrameChange = System.currentTimeMillis(); // last frame change timestamp
    }

    public void movePacman(Maze maze) {

        for (int i=0; i<getSpeed(); i++) {
            if (pacman_x % CoordinateConverter.block_size == 0 &&
                pacman_y % CoordinateConverter.block_size == 0) {

                if ((request_dx != 0 || request_dy != 0) &&
                        (!maze.hasWall(
                                pacman_x,
                                pacman_y,
                                request_dx,
                                request_dy)
                        &&
                        !maze.hasDoor(
                                pacman_x,
                                pacman_y,
                                request_dx,
                                request_dy)
                        )) {
                    pacman_dx = request_dx;
                    pacman_dy = request_dy;
                    request_dx = request_dy = 0;

                }
                if (maze.hasWall(
                        pacman_x,
                        pacman_y,
                        pacman_dx,
                        pacman_dy)
                        ||
                    maze.hasDoor(
                        pacman_x,
                        pacman_y,
                        pacman_dx,
                        pacman_dy
                    ))
                {
                    pacman_dx = 0;
                    pacman_dy = 0;

                }
            }
            pacman_x = mod(pacman_x+pacman_dx, CoordinateConverter.toScreenCoordinate(maze.getWidth()));
            pacman_y += pacman_dy;
        }
        // Update animation frame for pac-man
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastFrameChange > FRAME_INTERVAL) {
            currentFrame = (currentFrame + 1) % left.size(); // % to be sure that we dont have index out of bounds
            lastFrameChange = currentTime;
        }
    }




    private int mod(int value, int mod) {
        while(value<0) value += mod;
        return value%mod;
    }

    public int getX() {
        return pacman_x;
    }

    public int getY() {
        return pacman_y;
    }

    private int getNewCoordinate(int coordinate, int delta){
        return coordinate+delta*getSpeed();//6;
    }

    public void handleKeyPressed(int keyCode) {
        switch (keyCode) {
            case KeyEvent.VK_LEFT -> {
                request_dx = -1;
                request_dy = 0;
            }
            case KeyEvent.VK_RIGHT -> {
                request_dx = 1;
                request_dy = 0;
            }
            case KeyEvent.VK_UP -> {
                request_dx = 0;
                request_dy = -1;
            }
            case KeyEvent.VK_DOWN -> {
                request_dx = 0;
                request_dy = 1;
            }
        }
    }
    public void drawPacman(Graphics2D g2d)  {
        ArrayList<Image> currentSequence = right;
        if (pacman_dx == -1) {
            currentSequence = left;
        } else if (pacman_dy == 1) {
            currentSequence = down;
        } else if (pacman_dy == -1) {
            currentSequence = up;
        }
        g2d.drawImage(currentSequence.get(currentFrame), pacman_x, pacman_y, 22,22, null);
    }
}
