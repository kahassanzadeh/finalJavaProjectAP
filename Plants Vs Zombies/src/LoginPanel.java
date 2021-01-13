import javax.swing.*;
import java.awt.*;

/**
 * this class created for the login Panel of the game
 *
 * @author Mohammadreza Hassanzadeh
 * @since Jan 13 2021
 * @version 1.0
 */
public class LoginPanel {
    //frame of the loginMenu
    private JFrame frame;

    /**
     * constructor for this menu
     */
    public LoginPanel(){
        frame = new JFrame();
        frame.setLocation(500,300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(400,200));
        JPanel panel = new JPanel(new BorderLayout(10,10));
        frame.setContentPane(panel);

        JLabel label = new JLabel("Please enter your username and password");
        label.setPreferredSize(new Dimension(150, 50));
        label.setBackground(Color.lightGray);
        label.setBorder(BorderFactory.createLineBorder(Color.BLACK,2));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setFont(new Font("Calibri",Font.BOLD,17));



        JLabel userNameLabel = new JLabel("User Name : ");
        JTextField userNameField = new JTextField();
        userNameLabel.setHorizontalAlignment(SwingConstants.CENTER);


        JLabel passwordLabel = new JLabel("Password : ");
        passwordLabel.setHorizontalAlignment(SwingConstants.CENTER);
        JPasswordField passwordField = new JPasswordField();


        JPanel fields = new JPanel(new GridLayout(2,2,5,5));
        fields.add(userNameLabel);
        fields.add(userNameField);
        fields.add(passwordLabel);
        fields.add(passwordField);

        JButton loginButton = new JButton("Login");


        panel.add(label,BorderLayout.NORTH);
        panel.add(fields,BorderLayout.CENTER);
        panel.add(loginButton,BorderLayout.SOUTH);
    }

    /**
     * setting the frame visibility true
     */
    public void showLoginPanel(){
        frame.pack();
        frame.setVisible(true);

    }

    /**
     * setting the frame visibility false
     */
    private void closeLoginPanel(){
        frame.setVisible(false);
    }
}
