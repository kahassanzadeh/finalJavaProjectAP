
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;

/**
 * this class created for UserPanel
 *
 * @author Mohammadreza Hassanzadeh
 * @since Jan 11 2021
 * @version 1.0
 */
public class UserPanel implements Serializable {

    //main frame
    private JFrame frame;

    private Person registeredPerson;

    private String difficulty = "Normal";

    public UserPanel(Person registeredPerson) throws IOException {

        //setting the main frame of the panel
        frame = new JFrame();
        frame.setLocation(100,100);
        frame.setSize(1150,600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.registeredPerson = registeredPerson;


        BufferedImage image = ImageIO.read(new File("E:\\university\\5th term\\AP\\Final Project\\PVS Design Kit\\images\\first_screen.jpg"));
        JLabel imageLabel = new JLabel(new ImageIcon(image));

        //button for the game menu
        JButton newGame = new JButton("1.New Game");
        JButton loadGame = new JButton("2.Load Game");
        JButton ranking = new JButton("3.Ranking");
        JButton setting = new JButton("4.Setting");
        JButton quitGame = new JButton("5.Quit Game");

        newGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registeredPerson.beginGame(difficulty);
                closeUserPanel();
            }
        });

        loadGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame();
                frame.setLocation(100,100);
                frame.setSize(300,300);
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

                JPanel mainPanel = new JPanel(new BorderLayout(5,5));

                String[] games = new String[registeredPerson.getAllOfGames().size()];
                try {
                    registeredPerson.updateGameMapsFromFile();
                } catch (IOException | ClassNotFoundException exception) {
                    exception.printStackTrace();
                }
                for(int i = 0; i < registeredPerson.getAllOfGames().size(); i++){
                    games[i] = registeredPerson.getAllOfGames().get(i).toString();
                }

                JList<String> list = new JList<>(games);

                JScrollPane scrollPane = new JScrollPane(list);
                scrollPane.setPreferredSize(new Dimension(300,200));

                JLabel label = new JLabel("Please Choose Your Game");
                label.setFont(new Font("Calibri",Font.BOLD,15));
                label.setPreferredSize(new Dimension(300,50));

                JButton loadGameButton = new JButton("Load");
                loadGameButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        try {
                            registeredPerson.loadGame(registeredPerson.findGameMap(list.getSelectedValue()));
                            frame.setVisible(false);
                            UserPanel.this.closeUserPanel();
                        } catch (Exception exception) {
                            System.out.print("ERROR");
                        }
                    }
                });

                mainPanel.add(scrollPane,BorderLayout.CENTER);
                mainPanel.add(label,BorderLayout.NORTH);
                mainPanel.add(loadGameButton,BorderLayout.SOUTH);

                frame.add(mainPanel);
                frame.setVisible(true);

            }
        });

        quitGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    registeredPerson.savePerson();
                    FileManager fm = new FileManager();
                    fm.saveAllOFPlayersToFile();
                } catch (IOException exception) {
                    exception.printStackTrace();
                }
                closeUserPanel();
                System.exit(1);
            }
        });

        newGame.setFont(fontOfTheButtons());
        loadGame.setFont(fontOfTheButtons());
        ranking.setFont(fontOfTheButtons());
        setting.setFont(fontOfTheButtons());
        quitGame.setFont(fontOfTheButtons());

        newGame.setSize(dimensionOfTheButtons());
        loadGame.setSize(dimensionOfTheButtons());
        ranking.setSize(dimensionOfTheButtons());
        setting.setSize(dimensionOfTheButtons());
        quitGame.setSize(dimensionOfTheButtons());

        newGame.setLocation(120,500);
        loadGame.setLocation(320,500);
        ranking.setLocation(520,500);
        setting.setLocation(720,500);
        quitGame.setLocation(920,500);

        frame.add(newGame);
        frame.add(loadGame);
        frame.add(ranking);
        frame.add(setting);
        frame.add(quitGame);



        frame.add(imageLabel);

    }

    /**
     * this method will make this panel visible
     */
    public void showUserPanel(){
        frame.pack();
        frame.setVisible(true);
    }

    /**
     * this method will close this panel
     */
    public void closeUserPanel(){
        frame.setVisible(false);
    }

    /**
     * setting the fonts of the buttons
     * @return Font of the buttons
     */
    public Font fontOfTheButtons(){
        return new Font("Source Sans Pro",Font.BOLD,14);
    }

    /**
     * setting the dimensions of the button
     * @return Dimensions of the buttons
     */
    public Dimension dimensionOfTheButtons(){
        return new Dimension(150,70);
    }
}
