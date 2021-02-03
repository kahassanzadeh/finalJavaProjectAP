import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * this panel created for the enrollment section to enroll new players
 * @author Mohammadreza Hassanzadeh
 * @version 1.1
 */
public class EnrollmentPanel {

    private JFrame frame;

    public EnrollmentPanel(){
        frame = new JFrame();
        frame.setLocation(500,300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(400,200));


        JPanel mainPanel = new JPanel(new BorderLayout(10,10));

        JLabel label = new JLabel("Please enter your username and password and name");
        label.setPreferredSize(new Dimension(150, 50));
        label.setBackground(Color.lightGray);
        label.setBorder(BorderFactory.createLineBorder(Color.BLACK,2));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setFont(new Font("Calibri",Font.BOLD,17));

        JLabel nameLabel = new JLabel("Name : ");
        JTextField nameField = new JTextField();
        nameLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel userNameLabel = new JLabel("User Name : ");
        JTextField userNameField = new JTextField();
        userNameLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel passwordLabel = new JLabel("Password : ");
        passwordLabel.setHorizontalAlignment(SwingConstants.CENTER);
        JTextField passwordField = new JTextField();

        JPanel panel = new JPanel(new GridLayout(3,2,5,5));
        panel.add(nameLabel);
        panel.add(nameField);
        panel.add(userNameLabel);
        panel.add(userNameField);
        panel.add(passwordLabel);
        panel.add(passwordField);

        JButton register = new JButton("Register");
        register.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    PlayerController.checkUserName(userNameField.getText());
                    Person temp = new Person(nameField.getText(),userNameField.getText(),passwordField.getText());
                    PlayerController.addPerson(temp);
                    FileManager fm = new FileManager();
                    fm.saveAllOFPlayersToFile();
                    closingEnrollmentPanel();
                }catch(Exception exception){
                    JOptionPane.showMessageDialog(frame,exception.getMessage(),"ERROR",JOptionPane.ERROR_MESSAGE);
                    userNameField.setText("");
                }
            }
        });

        mainPanel.add(label,BorderLayout.NORTH);
        mainPanel.add(panel,BorderLayout.CENTER);
        mainPanel.add(register,BorderLayout.SOUTH);
        frame.setContentPane(mainPanel);


    }

    /**
     * showing the enrollment GUI
     */
    public void showEnrollmentPanel(){
        frame.setVisible(true);
        frame.pack();
    }

    /**
     * closing the GUI
     */
    public void closingEnrollmentPanel(){
        frame.setVisible(false);
    }
}
