package pacman.Windows;

import Utils.FileUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public abstract class CommonScreen extends JPanel
        implements ActionListener {

    protected static final int defaultWidth = 1050;
    protected static final int defaultHeight = 750;
    public CommonScreen() {
        setBackground(Color.BLACK);

        initializeComponents();

        setBackground(Color.BLACK);
        setLayout(null);
        setVisible(true);
        setEnabled(true);
        addComponentListener(new ComponentListener() {
            @Override
            public void componentResized(ComponentEvent e) {
                var size = getSize();
                handleResize(size.width, size.height);
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

    protected void handleResize(int newWidth, int newHeight){

    }
          
    protected void addImage(String imagePath, int left, int top) { // , int width, int height) {
        var image = FileUtils.loadImageIcon(imagePath);
        var label = new JLabel(image);
        label.setBounds(left, top, image.getIconWidth(), image.getIconHeight());
        this.add(label);
    }

    protected JButton addImageButton(String imagePath, String actionCommand, int top) {
        var btnIcon = FileUtils.loadImageIcon(imagePath);

        var button = new JButton(btnIcon);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setActionCommand(actionCommand);
        button.addActionListener(this);
        button.setBounds(335, top, btnIcon.getIconWidth(), btnIcon.getIconHeight());
        this.add(button);
        return button;
    }


    protected abstract void initializeComponents();

    public abstract void actionPerformed(ActionEvent e);
}
