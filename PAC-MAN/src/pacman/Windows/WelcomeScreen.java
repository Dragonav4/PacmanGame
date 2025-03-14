package pacman.Windows;
import Utils.FileUtils;
import pacman.core.MusicMixer;
import pacman.Pacman;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.*;

public class WelcomeScreen extends CommonScreen {

    private JButton soundButton, musicButton;
    private static final Font SFont = FileUtils.loadFont("fonts/emulogic.ttf", Font.BOLD, 12);
    private static ImageIcon soundOffIcon=FileUtils.loadImageIcon("images/btnSoundOff.png");
    private static ImageIcon soundOnIcon=FileUtils.loadImageIcon("images/btnSoundOn.png");
    private static ImageIcon musicOffIcon=FileUtils.loadImageIcon("images/btnMusicOff.png");
    private static ImageIcon musicOnIcon=FileUtils.loadImageIcon("images/btnMusicOn.png");
    private static final String StartGameAction="StartGameAction";
    private static final String HighscoresAction="HighscoresAction";
    private static final String ExitAction="ExitAction";
    private static final String ToggleMusicAction="ToggleMusicAction";
    private static final String ToggleSoundAction="ToggleSoundAction";
    public static String PlayerName = "Player";

    private JTextField nameField;
    protected void initializeComponents() {
        nameField = new JTextField();
        nameField.setBackground(Color.ORANGE);
        nameField.setForeground(Color.RED);
        nameField.setBounds(335, 300, 360, 35);
        nameField.setFont(SFont);
        nameField.setHorizontalAlignment(SwingConstants.CENTER);
        nameField.setText(PlayerName);
        nameField.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent e) {
                PlayerName = nameField.getText();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                insertUpdate(e);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                insertUpdate(e);
            }
            
        });


        add(nameField);
        
        addImage("images/gameLogo.png", 242, 98);
        addImageButton("images/btnStart.png", StartGameAction, 368);
        addImageButton("images/btnHighscores.png", HighscoresAction, 565);
        addImageButton("images/btnExit.png", ExitAction, 640);
        soundButton = addImageButton("images/btnSoundOff.png", ToggleSoundAction, 441);
        musicButton = addImageButton("images/btnMusicOff.png", ToggleMusicAction, 505);

        addImage("images/background2.gif", 0, 20);

        setMusicButtonIcon(MusicMixer.getInstance().isMusicOn());
        setSoundButtonIcon(MusicMixer.getInstance().isMusicOn());


    }

    private void setMusicButtonIcon(Boolean isOn) {
        musicButton.setIcon(isOn ? musicOnIcon : musicOffIcon);
    }

    private void setSoundButtonIcon(Boolean isOn) {
        soundButton.setIcon(isOn ? soundOnIcon : soundOffIcon);
    }


    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case StartGameAction: {
                StartGameScreen.createAndShow();
                break;
            }
            case ToggleMusicAction: {
                setMusicButtonIcon(MusicMixer.getInstance().toggleMusic());
                break;
            }
            case ToggleSoundAction: {
                setSoundButtonIcon(MusicMixer.getInstance().toggleSound());
                break;
            }
            case HighscoresAction: {
                HighscoreScreen.createAndShow();
                break;
            }
            case ExitAction: {
                System.exit(0); // normal application shutdown
                break;
            }
        }
    }

    public static void createAndShow() {
        Pacman.setMainFrame(new WelcomeScreen());
    }
}
