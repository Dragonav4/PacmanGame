package pacman.core;

import pacman.Pacman;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;


public class MusicMixer {
    public Boolean _musicOn = false;
    public Boolean _soundOn = false;


    private AudioInputStream audioInputStream;
    private Clip clip;
    private Clip eatSoundClip;

    public static MusicMixer getInstance() {
        return _instance;
    }

    private static MusicMixer _instance = new MusicMixer();

    private MusicMixer() {
        try {
            var resourceLocation = Pacman.class.getResource("sounds/pacman_eatfruit.wav");
            var audioInputStream = AudioSystem.getAudioInputStream(resourceLocation); //new File(soundName).getAbsoluteFile());
            eatSoundClip = AudioSystem.getClip();
            eatSoundClip.open(audioInputStream);
        }
        catch(Exception ex) {
            System.err.println(ex.getMessage());
        }
        toggleMusic();
        toggleSound();
    } 
    
    public Boolean isMusicOn() {
        return _musicOn;
    }


    public Boolean toggleMusic() {
        _musicOn = !_musicOn;
        if (_musicOn) {
            try{
            // start play music
            var resourceLocation = Pacman.class.getResource("sounds/mainLoop.wav");
            audioInputStream = AudioSystem.getAudioInputStream(resourceLocation); //new File(soundName).getAbsoluteFile());
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
            clip.loop(clip.LOOP_CONTINUOUSLY);
            }
            catch (Exception ex) {

            }
        }
        else {
            // stop playing music
            if (clip != null)
                clip.stop();
        }
        return isMusicOn();
    }

    public Boolean isSoundOn() {
        return _soundOn;
    }

    public Boolean toggleSound() {
        _soundOn = !_soundOn;

        return isSoundOn();
    }


    public void playBackgroundSound() {
        if (_soundOn) {
//            eatSoundClip.start();
//            eatSoundClip.loop(eatSoundClip.LOOP_CONTINUOUSLY);
        }
    }

    public void stopBackgroundSound() {
        if (eatSoundClip.isRunning())
            eatSoundClip.stop();
    }

    public void playEatSound() {

        if (_soundOn && !eatSoundClip.isRunning()) {
                var playThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    eatSoundClip.setFramePosition(0);
                    eatSoundClip.start();
                }
            });
            playThread.start();
        }
    }

    public Boolean playDeathSound() {
        try{
            // start play music
            var resourceLocation = Pacman.class.getResource("sounds/pacman_death.wav");
            audioInputStream = AudioSystem.getAudioInputStream(resourceLocation); //new File(soundName).getAbsoluteFile());
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        }
        catch (Exception ex) {

        }
        return isSoundOn();
    }
}
