import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

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
    public LoginPanel() {
        try{
            PlayerController.renewPlayers();
        }catch(Exception ignored){
            System.out.println("Error");
        }
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

        JPanel buttons = new JPanel(new GridLayout(1,2,5,5));


        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Person registeredPerson;
                try {
                    registeredPerson = PlayerController.searchPerson(userNameField.getText(), String.valueOf(passwordField.getPassword()));
                    registeredPerson.showUserPanel();
                    closeLoginPanel();
                } catch (Exception exception) {
                    JOptionPane.showMessageDialog(frame,"Invalid UserName or Password, PLease Try Again","Error",JOptionPane.ERROR_MESSAGE);
                    passwordField.setText("");
                    userNameField.setText("");
                }
            }
        });

        JButton newPersonEnroll = new JButton("Make An Account");
        newPersonEnroll.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EnrollmentPanel enrollment = new EnrollmentPanel();
                enrollment.showEnrollmentPanel();
            }
        });

        buttons.add(loginButton);
        buttons.add(newPersonEnroll);


        panel.add(label,BorderLayout.NORTH);
        panel.add(fields,BorderLayout.CENTER);
        panel.add(buttons,BorderLayout.SOUTH);
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
