package pacman.core;
import pacman.Pacman;
import pacman.characters.CharacterPacman;
import pacman.characters.Ghost.Ghost;
import pacman.characters.Ghost.GhostsCollection;
import pacman.PowerUp.PowerUp;
import pacman.PowerUp.PowerUpCollection;
import pacman.UI.LiveCounter;
import pacman.UI.Score;
import pacman.Windows.HighscoreScreen;
import pacman.Windows.WelcomeScreen;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.*;

public class Game extends JPanel implements ActionListener {
    private GameState gameState = GameState.waitingStart;
    private final Thread mainThread;

    private final Font SFont = new Font("EMULOGIC", Font.BOLD, 12);
    Maze maze;
    public CharacterPacman characterPacman;
    public Score score = new Score();
    public LiveCounter liveCounter = new LiveCounter();
    GhostsCollection ghosts;
    PowerUpCollection powerUpCollection;
    private int level = 1;
    int frameRateMs = 30;
    private Game(int width, int height) {
        maze = new Maze(width,height, level);
        ghosts = new GhostsCollection(4, maze);
        powerUpCollection = new PowerUpCollection(ghosts);
        characterPacman = new CharacterPacman(maze.getWidth()/2,15);

        if (MusicMixer.getInstance().isMusicOn())
            MusicMixer.getInstance().toggleMusic();

        setFocusable(true);
        addKeyListener(new Game.TAdapter()); //
        mainThread = new Thread(() -> {
            try {
                while(gameState != GameState.gameOver) {
                    actionPerformed(null);
                    Thread.sleep(frameRateMs);
                }
            }
            catch(Exception ex){
                System.out.println(ex.getMessage());
            }
        });
        mainThread.start();
        setSize(1050, 750);
        setBackground(Color.BLACK);
        setVisible(true);
        setEnabled(true);
    }

    public void paintComponent(Graphics g) {
        g.setFont(SFont);
        super.paintComponent(g);


        Graphics2D g2d = (Graphics2D) g;
        g2d.scale((double)this.getBounds().width/ CoordinateConverter.toScreenCoordinate(maze.getWidth()+1),
                (double)this.getBounds().height/CoordinateConverter.toScreenCoordinate(maze.getHeight()+1));
        g2d.setColor(Color.black);

        maze.drawMaze(g2d);
        characterPacman.drawPacman(g2d);
        score.drawScore(g2d);
        liveCounter.drawLivesCounter(g2d, this);
        ghosts.drawGhosts(g2d,this);
        powerUpCollection.drawPowerUp(g2d, this);

        switch (gameState) {
            case waitingStart -> showIntroScreen(g2d);
            case gameOver -> showGameOverScreen(g2d);
            case paused -> showGamePausedScreen(g2d);
            case levelCompleted -> showLevelCompletedScreen(g2d);
        }

        Toolkit.getDefaultToolkit().sync(); //what
        g2d.dispose();
    }

    private void drawCenteredText(Graphics2D g2d, String text, Color color) {
        var x = (CoordinateConverter.toScreenCoordinate(maze.getWidth()) - text.length()*12)/2;
        var y = (CoordinateConverter.toScreenCoordinate(maze.getHeight()))/2-8;
        g2d.setColor(color);
        g2d.drawString(text, x, y);
    }

    public void showIntroScreen(Graphics2D g2d) {
        drawCenteredText(g2d, "Press SPACE to START", Color.yellow);
    }

    public void showGameOverScreen(Graphics2D g2d) {
        drawCenteredText(g2d, "GAME OVER :(", Color.yellow);
    }
    public void showGamePausedScreen(Graphics2D g2d) {
        drawCenteredText(g2d, "ESC FOR EXIT OR ANY KEY FOR CONTINUE", Color.yellow);
    }

    public void showLevelCompletedScreen(Graphics2D g2d) {
        drawCenteredText(g2d,"Level completed", Color.yellow);
    }


    public void startHunt() {
        ghosts.setHuntMode(true);
        Thread thread  = new Thread(() -> {
          while(true) {
              try {
                  Thread.sleep(10000);
                  ghosts.setHuntMode(false);
              } catch (InterruptedException e) {
                  Thread.currentThread().interrupt();
                  break;
              }
          }
        });
        thread.start();
    }




    class TAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();
            switch (gameState) {
                case waitingStart -> {
                    if (key == KeyEvent.VK_SPACE) {
                        gameState = GameState.inGame;
                        MusicMixer.getInstance().playBackgroundSound();
                    }
                }
                case inGame -> {
                    characterPacman.handleKeyPressed(key);
                    if(key == KeyEvent.VK_ESCAPE) {
                        gameState = GameState.paused;
                        MusicMixer.getInstance().stopBackgroundSound();

                    }
                }

                case paused -> {
                    if(key == KeyEvent.VK_SPACE
                            || key == KeyEvent.VK_LEFT
                            || key == KeyEvent.VK_RIGHT
                            || key == KeyEvent.VK_DOWN
                            || key == KeyEvent.VK_UP) {
                        gameState = GameState.inGame;
                        MusicMixer.getInstance().playBackgroundSound();

                    } else {
                        if(key ==KeyEvent.VK_ESCAPE) {
                            WelcomeScreen.createAndShow();
                        }
                    }
                }
                case gameOver -> {
                    MusicMixer.getInstance().stopBackgroundSound();
                    HighscoreScreen.createAndShow(score.getScore());
                }
                case dying -> {
                    if(key == KeyEvent.VK_SPACE
                            || key == KeyEvent.VK_LEFT
                            || key == KeyEvent.VK_RIGHT
                            || key == KeyEvent.VK_DOWN
                            || key == KeyEvent.VK_UP) {
                        gameState = GameState.inGame;

                    }
                }
                case levelCompleted -> {
                    if (key == KeyEvent.VK_SPACE) {

                        powerUpCollection.init();
                        gameState = GameState.inGame;

                        frameRateMs = frameRateMs * 12 / 16;
                        characterPacman.initialize(maze.getWidth()/2,15);
                        ghosts.init(4);

                        level++;
                        maze.load(level);
                    }
                }
            }
        }
    }


    public void actionPerformed(ActionEvent actionEvent) {
        if(gameState == GameState.inGame) {
            ghosts.moveGhosts(maze);
            characterPacman.movePacman(maze);
            if (maze.hasFood(characterPacman.getX(), characterPacman.getY())) {
                maze.removeFoodInPosition(characterPacman.getX(), characterPacman.getY());
                score.increment(1);
                 MusicMixer.getInstance().playEatSound();

                if(score.getScore() > 0 && score.getScore() % 100 == 0) {
                    ghosts.addGhost();
                }
                if (!maze.containFood()) {
                    MusicMixer.getInstance().stopBackgroundSound();
                    gameState = GameState.levelCompleted;
                }
            }
            Ghost ghost;
            if ((ghost=ghosts.findInPosition(characterPacman)) != null) {
                if (characterPacman.isInvulnerable) { // isInvulnerable = protection from the ghost
                    score.increment(250);
                    ghosts.removeGhost(ghost);
                    characterPacman.isInvulnerable = false;
                }
                else if (ghost.huntMode) {
                    score.increment(250);
                    ghosts.removeGhost(ghost);
                }
                else {
                    liveCounter.decrement();
                    MusicMixer.getInstance().stopBackgroundSound();
                    MusicMixer.getInstance().playDeathSound();
                    characterPacman.initialize(maze.getWidth()/2,15);
                    ghosts.init(4);
                    if (liveCounter.getLives() == 0) {
                        gameState = GameState.gameOver;
                    } else {
                        gameState = GameState.dying;
                    }


                }

            }
            PowerUp powerUp;
            if((powerUp = powerUpCollection.findInPosition(characterPacman)) != null) {
                powerUp.apply(this);
            }


        }
        repaint();
    }

    public static void Show(int width, int height) {
        Pacman.setMainFrame(new Game(width, height));
    }
}


