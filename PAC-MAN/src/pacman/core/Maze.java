package pacman.core;

import pacman.Pacman;

import java.awt.*;
import java.io.File;
import java.util.Scanner;

public class Maze {
    static final int wall = 1;
    static final int food = 2;
    static final int door = 3;
    public int[][] levelData;
    private int size_x, size_y;

    private static final BasicStroke wallsStroke = new BasicStroke(4);
    public Maze(int size_x, int size_y, int level) {
        this.size_x = size_x;
        this.size_y = size_y;
        load(level);
    }


    public void load(int level) {
        this.levelData = new int[size_x][size_y];
        var mapFileName = "maps/map"+size_x+"x"+size_y+"_"+(level%10)+".txt";

        var fullPath = Pacman.class.getResource(mapFileName).toString().substring(5); //to remove first 5 characters(file:/...)
        levelData = new int[size_x][size_y];

        try {
            var myMapFile = new File(String.valueOf(fullPath));
            var scanner = new Scanner(myMapFile);

            for (int y = 0; y < size_y; y++) {
                var data = scanner.nextLine();
                for(int x =0; x < size_x; x++) {
                    levelData[x][y] = convertToInt(data.charAt(x));
                }
            }
            scanner.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public int getWidth() {
        return levelData.length; //x
    }

    private static int convertToInt(char c) {
        return switch (c) {
            case '|' -> wall;
            case '.' -> food;
            case '-' -> door;
            default -> 0;
        };
    }

    public void drawMaze(Graphics2D g2d) {
        for (int x = 0; x < levelData.length; x++) {
            for (int y = 0; y < levelData[0].length; y++) {
                switch (levelData[x][y]) {
                    case wall -> {

                        drawWall(g2d, CoordinateConverter.toScreenCoordinate(x),CoordinateConverter.toScreenCoordinate(y));
                    }
                    case food -> {
                        g2d.setColor(Color.black);
                        g2d.drawRect(
                                CoordinateConverter.toScreenCoordinate(x),
                                CoordinateConverter.toScreenCoordinate(y),
                                CoordinateConverter.block_size,
                                CoordinateConverter.block_size);
                        g2d.fillRect(
                                CoordinateConverter.toScreenCoordinate(x),
                                CoordinateConverter.toScreenCoordinate(y),
                                CoordinateConverter.block_size,
                                CoordinateConverter.block_size);
                        g2d.setColor(Color.white);
                        g2d.fillOval(
                                CoordinateConverter.toScreenCoordinate(x) + 10,
                                CoordinateConverter.toScreenCoordinate(y) + 10, 6, 6);
                    }
                    case door -> {
                        g2d.setColor(Color.ORANGE);
                        g2d.fillRect(
                                CoordinateConverter.toScreenCoordinate(x),
                                CoordinateConverter.toScreenCoordinate(y)+CoordinateConverter.block_size/2-2,
                                CoordinateConverter.block_size, 4);


                    }
                    default -> {
                        g2d.setColor(Color.black);

                        g2d.drawRect(
                                CoordinateConverter.toScreenCoordinate(x),
                                CoordinateConverter.toScreenCoordinate(y),
                                CoordinateConverter.block_size,
                                CoordinateConverter.block_size);
                        g2d.fillRect(
                                CoordinateConverter.toScreenCoordinate(x),
                                CoordinateConverter.toScreenCoordinate(y),
                                CoordinateConverter.block_size,
                                CoordinateConverter.block_size);
                    }
                }
            }
        }
    }


    private int mod(int value, int mod) {
        while(value<0) {
            value+=mod;
        }
        return value%mod;
    }
    private int getLevelData(int x, int y) {

        return levelData[mod(x,getWidth())][mod(y,getHeight())];
    }



    private void drawWall(Graphics2D g2d, int ScreenX, int ScreenY) {
        Color wallColor = new Color(83, 10, 140);
        g2d.setColor(wallColor);
        int x =  ScreenX / CoordinateConverter.block_size;
        int y =  ScreenY / CoordinateConverter.block_size;


        if (isWallOnLeft(x, y) && !isWallOnRight(x, y) && !isWallOnTop(x, y) && isWallOnBottom(x, y) ||
                isWallOnLeft(x, y) && isWallOnBottom(x, y) && !isWall(x-1, y+1) ||
                !isWallOnTop(x, y) && isWallOnBottom(x, y) && x==0 && !isWall(x+1, y)) {
            g2d.setStroke(wallsStroke); // draw '┐'
            g2d.drawArc(x*24-12, y*24+12, 24, 24, 0, 90);
            return;
        }
        if (!isWallOnLeft(x, y) && isWallOnRight(x, y) && !isWallOnTop(x, y) && isWallOnBottom(x, y) ||
                isWallOnRight(x, y) && isWallOnBottom(x, y) && !isWall(x+1, y+1) ||
                !isWallOnTop(x, y) && isWallOnBottom(x, y) && x==levelData.length-1 && y>0) {
            g2d.setStroke(wallsStroke); // draw '┌'
            g2d.drawArc(x*24+12, y*24+12, 24, 24, 90, 90);

            return;
        }
        if (!isWallOnLeft(x, y) && isWallOnRight(x, y) && isWallOnTop(x, y) && !isWallOnBottom(x, y) ||
                isWallOnRight(x, y) && isWallOnTop(x, y) && !isWall(x+1, y-1) ||
                isWallOnTop(x, y) && !isWallOnBottom(x, y) && x==levelData.length-1 && !isWall(x-1, y)) {
            g2d.setStroke(wallsStroke); // draw '└'
            g2d.drawArc(x*24+12, y*24-12, 24, 24, 180, 90);
            return;
        }
        if (isWallOnLeft(x, y) && !isWallOnRight(x, y) && isWallOnTop(x, y) && !isWallOnBottom(x, y) ||
                isWallOnLeft(x, y) && isWallOnTop(x, y) && !isWall(x-1, y-1) ||
                isWallOnTop(x, y) && !isWallOnBottom(x, y) && x==0 && !isWall(x+1, y)) {
            g2d.setStroke(wallsStroke); // draw '┘'
            g2d.drawArc(x*24-12, y*24-12, 24, 24, 270, 90);

            return;
        }

        if (isWallOnTop(x, y) && isWallOnBottom(x, y)) {
            g2d.setStroke(wallsStroke);
            g2d.drawLine(x*24+12, y*24, x*24+12, y*24+24);
            return;
        }

        if (isWallOnLeft(x, y) && isWallOnRight(x, y) ||
                !isWallOnLeft(x,y) && isWallOnRight(x, y) && isWallOnRight(x+1, y) ||
                !isWallOnRight(x,y) && isWallOnLeft(x, y) && isWallOnLeft(x-1, y)) {
            g2d.setStroke(wallsStroke); // draw '─'
            g2d.drawLine(x*24, y*24+12, x*24+24, y*24+12);
            return;
        }
        g2d.fillRect(x*24, y*24, 24, 24);

    }

    private boolean isWall(int x, int y) {
        return x>=0 && x<levelData.length && y>=0&&y<levelData[x].length && levelData[x][y] == wall;
    }

    private boolean isWallOnTop(int x, int y) {
        return isWall(x, y-1);
    }
    private boolean isWallOnBottom(int x, int y) {
        return isWall(x, y+1);
    }
    private boolean isWallOnLeft(int x, int y) {
        return isWall(x-1, y);
    }
    private boolean isWallOnRight(int x, int y) {
        return isWall(x+1, y);
    }



    public boolean hasWall(int screenX, int screenY, int dx, int dy) {
        int blockX = CoordinateConverter.fromScreenCoordinate(screenX) + dx;
        int blockY = CoordinateConverter.fromScreenCoordinate(screenY)+dy;
        return getLevelData(blockX,blockY) == wall;
    }

    public boolean hasDoor(int screenX, int screenY, int dx, int dy) {
        int blockX = CoordinateConverter.fromScreenCoordinate(screenX) + dx;
        int blockY = CoordinateConverter.fromScreenCoordinate(screenY)+dy;
        return getLevelData(blockX,blockY) == door;
    }

    public int getOpenedCells(int screenX, int screenY){
        var x=CoordinateConverter.fromScreenCoordinate(screenX);
        var y=CoordinateConverter.fromScreenCoordinate(screenY);
        return (getLevelData(x-1,y)==wall ? 0:1) + // left cell
                (getLevelData(x+1,y) ==wall ? 0:1) + // right cell
                (getLevelData(x,y-1)==wall ? 0:1) + // upper cell
                (getLevelData(x,y+1)==wall ? 0:1); // bottom cell
    }

    public boolean hasFood(int screenX, int screenY) {
        return hasItem(screenX + CoordinateConverter.block_size / 2, screenY + CoordinateConverter.block_size / 2, food);
    }

    private boolean hasItem(int screenX, int screenY, int itemType) {
        int blockX = CoordinateConverter.fromScreenCoordinate(screenX);
        int blockY = CoordinateConverter.fromScreenCoordinate(screenY);
        return getLevelData(blockX,blockY) == itemType;
    }

    public void removeFoodInPosition(int screenX, int screenY) {
        int blockX = CoordinateConverter.fromScreenCoordinate(screenX);
        int blockY = CoordinateConverter.fromScreenCoordinate(screenY);

        levelData[mod(blockX, getWidth())][blockY] = 0;
    }

    public boolean containFood() {
        for (var row : levelData)
            for (var cell : row)
                if (cell == food)
                    return true;
        return false;
    }

    public int getHeight() {
        return levelData[0].length;
    }
}
