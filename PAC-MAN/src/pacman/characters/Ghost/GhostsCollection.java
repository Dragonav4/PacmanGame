package pacman.characters.Ghost;

import pacman.core.CoordinateConverter;
import pacman.core.Maze;
import pacman.characters.CharacterPacman;

import java.awt.*;
import java.awt.image.ImageObserver;
import java.util.ArrayList;
import java.util.Random;

public class GhostsCollection {
    private ArrayList<Ghost> ghosts;

    private final int MAX_GHOSTS = 12; // MAX
    private int N_GHOSTS = 6; // at the beginning N_GHOSTS

    Random random = new Random();
    private Maze maze;

    public Ghost getRandomGhost() {
        for(int i=0;i<25; i++) {
            int randomGhost = random.nextInt(ghosts.size());
            var ghost = ghosts.get(randomGhost);
            if (!inInitialBox(ghost))
                return ghost;
        }
        return null;
    }

    private boolean inInitialBox(Ghost ghost) {
        var mapCentre= maze.getWidth()/2;
        var mapX = CoordinateConverter.fromScreenCoordinate(ghost.getX());
        var mapY = CoordinateConverter.fromScreenCoordinate(ghost.getY());
        return (mapY == 13 || mapY==12) &&
                mapX >= mapCentre-3 &&
                mapX < mapCentre + 3;
    }

    public GhostsCollection(int amount, Maze maze) {
        this.maze = maze;
        init(amount);
    }

    public void addGhost() {
        if(ghosts.size() < MAX_GHOSTS) {
            ghosts.add(createGhost());
        }
    }




    private Ghost createGhost() {
        var mapCentre= maze.getWidth()/2-3;
        switch(ghosts.size() % 4) {
            case 0: return new BlinkyGhost(mapCentre+ghosts.size(), 13, ghosts.size() % 4);
            case 1: return new ClydeGhost(mapCentre+ghosts.size(), 13, ghosts.size() % 4);
            case 2: return new inkyGhost(mapCentre+ghosts.size(), 13, ghosts.size() % 4);
            case 3: return new PinkyGhost(mapCentre+ghosts.size(), 13, ghosts.size() % 4);
            default: return null;
        }
    }
    public void init(int amount){
        ghosts = new ArrayList<>();
        for(int i=0; i < amount; i++) {
            addGhost();
        }
    }





    public void drawGhosts(Graphics2D g2d, ImageObserver imageObserver) {
        for (Ghost ghost : ghosts)
            ghost.drawGhost(g2d, imageObserver);
    }

    public void moveGhosts(Maze maze) {
        for (Ghost ghost : ghosts)
        {
            ghost.moveGhost(maze);
        }
    }

    public Ghost findInPosition(CharacterPacman pacman) {
        for (Ghost ghost : ghosts)
            if (ghost.meet(pacman))
                return ghost;
        return null;
    }

    public void removeGhost(Ghost ghost) {
        ghosts.remove(ghost);

        Thread removeGhost = new Thread(() -> {
           try {
            Thread.sleep(3000);
            ghost.reset();
            ghost.huntMode = false;
            ghosts.add(ghost);
           } catch (InterruptedException e) {
               throw new RuntimeException(e);
           }

        });
        removeGhost.start();
    }

    public void setHuntMode(boolean huntMode) {
        for(var ghost : ghosts) {
            ghost.huntMode = huntMode;
        }
    }
}
