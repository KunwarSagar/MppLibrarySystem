package librarysystem;

import exceptions.LoginException;
import librarysystem.validation.Validation;
import exceptions.ValidationException;
import librarysystem.validation.ValidationFactory;
import controllers.SystemController;

import javax.swing.*;
import java.awt.*;


//this is the first view that executes when the program runs
//{101=[101:xyz, LIBRARIAN], 102=[102:abc, ADMIN], 103=[103:111, BOTH]}
public class LoginScreen extends JFrame implements LibrarySystemWindow {

    public static final LoginScreen INSTANCE = new LoginScreen();
    private boolean isInitialized = false;

    @Override
    public void init() {

        //this.setVisible(true);
        this.setSize(ConstantConfig.APP_WIDTH, ConstantConfig.APP_HEIGHT);
         createMyGUI();
         add(mainPanel);
    }
    public boolean isInitialized() {
        return isInitialized;
    }
    public void isInitialized(boolean val) {
        isInitialized = val;
    }

    //declaring variables
    JPanel body;
    JPanel mainPanel;
    JPanel buttonPanel;

    private JLabel title;
    private JLabel empyty;
    JLabel usernameLabel;
    JTextField usernameField;
    private JLabel passowrdLabel;
    JPasswordField passwordField;

    JButton loginButton;

    private LoginScreen() {UIController.INSTANCE.loginScreen = this;}

    public JTextField getUserNameField() {
        return usernameField;
    }

    public JPasswordField getPasswordField() {
        return passwordField;
    }

    private void createMyGUI() {

        //creating new object of respective swing classes
        mainPanel = new JPanel();
        title = new JLabel();
        body = new JPanel();
        usernameField = new JTextField();
        passwordField = new JPasswordField();
        usernameLabel = new JLabel();
        passowrdLabel = new JLabel();
        buttonPanel = new JPanel();
        loginButton = new JButton();

        //this is the main panel
        mainPanel.setBackground(new java.awt.Color(100, 204, 255));
        mainPanel.setToolTipText("");
        mainPanel.setLayout(null);

        //this is the title
        title.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        title.setText("Enter Credentials");
        mainPanel.add(title);
        title.setBounds(420, 40, 198, 40);

        body.setLayout(null);

        //assigning values to respective objects
        body.add(usernameField);
        usernameField.setBounds(10, 50, 210, 40);


        body.add(passwordField);
        passwordField.setBounds(10, 130, 210, 40);

        //this is for username
        usernameLabel.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        usernameLabel.setText("Username");
        body.add(usernameLabel);
        usernameLabel.setBounds(20, 20, 120, 30);

        //this is for password
        passowrdLabel.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        passowrdLabel.setText("Password");
        body.add(passowrdLabel);
        passowrdLabel.setBounds(20, 104, 120, 30);


        //login button
        BorderLayout bl = new BorderLayout();

        buttonPanel.setLayout(bl);
        loginButton.setText("Login");

        body.add(loginButton);
        loginButton.setBounds(30, 210, 100, 40);
        addLoginButtonListener(loginButton);


        mainPanel.add(body);
        body.setBounds(400, 100, 230, 270);

        getContentPane().add(mainPanel);
        mainPanel.setBounds(100, 100, ConstantConfig.APP_WIDTH, ConstantConfig.APP_HEIGHT);
        this.setResizable(false);

    }

    private void addLoginButtonListener(JButton button) {
        button.addActionListener(evt -> {
            SystemController sys = new SystemController();
            try{

                Validation loginValidations = ValidationFactory.getValidation(LoginScreen.this);
                loginValidations.checkValidation(LoginScreen.this);

                String username = usernameField.getText();
                char[] pass = passwordField.getPassword();
                String password = new String(pass);


                // Call controller
                sys.login(username,password);

            }catch(LoginException | ValidationException ex){
               new MessageBox.InnerFrame().showMessageBox(ex.getMessage(), "Error");
            }
        });
    }

}
