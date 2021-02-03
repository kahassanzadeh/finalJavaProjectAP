import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.Serializable;

/**
 * this class created for the menu for pausing and saving game
 * @author mohammadreza hassanzadeh
 * @version 1.1
 */
public class InsideGameMenuPanel implements Serializable {
    //frame for the panel
    private JFrame frame;
    //controller for the game
    private GameController gameController;




    public InsideGameMenuPanel(GameController gameController){
        this.gameController = gameController;
        frame = new JFrame();
        frame.setLocation(500,300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(400,200));

        JPanel mainPanel = new JPanel(new GridLayout(3,1,5,5));

        JButton resumeGame = new JButton("1.Resume Game");
        JButton saveGame = new JButton("2.Save Game");
        JButton exitToMainMenu = new JButton("3.Exit To Main Menu");

        resumeGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameController.resumeGame();
                closePausePanel();
            }
        });

        saveGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    gameController.getGameMap().getPerson().saveThisGame(gameController.getGameMap());
                } catch (IOException exception) {
                    exception.printStackTrace();
                }
                gameController.setIsSaved(true);
            }
        });

        exitToMainMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameController.getGameMap().dispose();
                gameController.getGameMap().closeGameMap();
                gameController.getGameMap().getPerson().showUserPanel();
                closePausePanel();
            }
        });

        mainPanel.add(resumeGame);
        mainPanel.add(saveGame);
        mainPanel.add(exitToMainMenu);

        frame.add(mainPanel);
    }

    public void showPausePanel(){
        frame.pack();
        frame.setVisible(true);
    }

    private void closePausePanel(){
        frame.setVisible(false);
    }

}
