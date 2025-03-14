package pacman.Windows;

import java.awt.event.*;
import pacman.core.Game;
import pacman.Pacman;

public class StartGameScreen extends CommonScreen {

    protected void initializeComponents() {
        addImage("images/gameLogo.png", 242, 98);
        addImageButton("images/btn24x24.png", "START_GAME_1", 300);
        addImageButton("images/btn26x26.png", "START_GAME_2", 360);
        addImageButton("images/btn28x28.png", "START_GAME_3", 420);
        addImageButton("images/btn30x30.png", "START_GAME_4", 480);
        addImageButton("images/btn32x32.png", "START_GAME_5", 540);
        addImageButton("images/btnExit.png", "BACK", 620);
        addImage("images/background2.gif", 0, 20);
    }

    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "START_GAME_1": {
                Game.Show(28, 19);
                break;
            }
            case "START_GAME_2": {
                Game.Show(34, 19);
                break;
            }
            case "START_GAME_3": {
                Game.Show(34, 22);
                break;
            }
            case "START_GAME_4": {
                Game.Show(40, 22);
                break;
            }
            case "START_GAME_5": {
                Game.Show(40, 25);
                break;
            }
            case "BACK": {
                WelcomeScreen.createAndShow();
                break;
            }
        }
    }

    public static void createAndShow() {
        Pacman.setMainFrame(new StartGameScreen());
    }
}
