package pacman.Windows;
import Utils.FileUtils;
import pacman.Pacman;

import java.awt.Font;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.util.TreeSet;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class HighscoreScreen extends CommonScreen {
    private static class ScoreRecord implements Comparable<ScoreRecord> {
        public String playerName;
        public int score;
        public ScoreRecord(String playerName, int score) {
            this.playerName = playerName;
            this.score = score;
        }
        @Override
        public int compareTo(HighscoreScreen.ScoreRecord other) {
            return other.score-score;
        }
    }

    private static final Font SFont = FileUtils.loadFont("fonts/emulogic.ttf", Font.BOLD, 12);
    private JTable scoreTable;
    private static String data[][]={{"Dalilian", "66000"}};
    private static final String column[] = { "NAME", "SCORE" };
    private static TreeSet<ScoreRecord> sortedData;
    @Override
    protected void initializeComponents() {
        if (sortedData == null) {
            sortedData = new TreeSet<>();
            sortedData.add(new ScoreRecord("Danilian Dan", 66666));
        }
        scoreTable = new JTable(data, column) {
            public boolean isCellEditable(int nRow, int nCol) {
                return false;
            }
            
        };

        scoreTable.setCellSelectionEnabled(false);
        scoreTable.setBackground(Color.BLACK);
        scoreTable.setForeground(Color.YELLOW);
        scoreTable.setFont(SFont);
        scoreTable.getColumnModel().getColumn(1).setMaxWidth(300);
        scoreTable.setBounds(100, 100, 800, 400);
        JScrollPane sp = new JScrollPane(scoreTable);
        sp.setBounds(scoreTable.getBounds());
        add(sp);

        addImageButton("images/btnExit.png", "BACK", 620);
        addImage("images/background2.gif", 0, 20);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {

            case "BACK": {
                WelcomeScreen.createAndShow();
                break;
            }
        }
    }

    private HighscoreScreen() {
        super();
    }

    private void addRecord(String playerName, int score) {
        sortedData.add(new ScoreRecord(playerName, score));
        data=new String[sortedData.size()][2];
        int row=0;
        for(var item:sortedData){
            data[row][0]=item.playerName;
            data[row][1]=""+item.score;
            row++;
        }
        var tableModel = new DefaultTableModel(data, column);
        scoreTable.setModel(tableModel);
        scoreTable.repaint();
    }

    public static void createAndShow() {
        Pacman.setMainFrame(new HighscoreScreen());
    }

    public static void createAndShow(String playerName, int score) {
        var highScoreScreen = new HighscoreScreen();
        highScoreScreen.addRecord(playerName, score);
        Pacman.setMainFrame(highScoreScreen);
    }

    public static void createAndShow(int score) {
        createAndShow(WelcomeScreen.PlayerName, score);
    }
}
