package pacman;

import pacman.Windows.WelcomeScreen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

public class Pacman extends JFrame  {

    private Pacman() {
        setVisible(true);
        setTitle("Pacman");
        setSize(1024,768);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        var pane = this.getContentPane();
        pane.setBackground(Color.BLACK);
        pane.setLayout(null);
        addComponentListener(new ComponentListener() {
            @Override
            public void componentResized(ComponentEvent e) {
                var size = getSize();
                var component = getContentPane().getComponentAt(0,0); //area of the window
                component.setSize(size);
            }

            @Override
            public void componentMoved(ComponentEvent e) {
            }

            @Override
            public void componentShown(ComponentEvent e) {
            }

            @Override
            public void componentHidden(ComponentEvent e) {
            }
        });
    }
    private static Pacman _instance;
    public static void main(String[] args) {
        _instance = new Pacman();
        setMainFrame(new WelcomeScreen());
    }
        public static void setMainFrame(JPanel newPanel){
            var size = _instance.getSize();
            newPanel.setSize(size);
            _instance.getContentPane().setBackground(Color.BLACK);
            _instance.getContentPane().removeAll();
            _instance.getContentPane().setSize(1050,750);


            _instance.getContentPane().add(newPanel);
            _instance.getContentPane().revalidate();
            newPanel.setFocusable(true);
            newPanel.requestFocusInWindow();
        }
}

