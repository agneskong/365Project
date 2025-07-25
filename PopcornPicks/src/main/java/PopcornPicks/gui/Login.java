package PopcornPicks.gui;

import PopcornPicks.db.MyJDBC;
import PopcornPicks.model.User;
import PopcornPicks.model.Session;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.sql.*;

public class Login extends JFrame {

    public Login() {
        setTitle("Popcorn Picks Login");
        setSize(1100, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        Color darkBG = new Color(20, 20, 20);
        getContentPane().setBackground(darkBG);
        setLayout(new BorderLayout());

        JPanel wrapper = new JPanel();
        wrapper.setBackground(darkBG);
        wrapper.setLayout(new BoxLayout(wrapper, BoxLayout.Y_AXIS));
        wrapper.add(Box.createVerticalGlue());

        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(darkBG);
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel titleRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        titleRow.setBackground(darkBG);

        JPanel titleBox = new JPanel();
        titleBox.setLayout(new BoxLayout(titleBox, BoxLayout.Y_AXIS));
        titleBox.setBackground(darkBG);

        JLabel popcornLabel = new JLabel("Popcorn");
        popcornLabel.setFont(new Font("Georgia", Font.BOLD | Font.ITALIC, 72));
        popcornLabel.setForeground(new Color(247, 179, 64));

        JLabel picksLabel = new JLabel("Picks");
        picksLabel.setFont(new Font("Georgia", Font.BOLD | Font.ITALIC, 72));
        picksLabel.setForeground(new Color(247, 179, 64));
        picksLabel.setBorder(BorderFactory.createEmptyBorder(0, 170, 0, 0));

        titleBox.add(popcornLabel);
        titleBox.add(picksLabel);

        JLabel logoLabel = new JLabel();
        URL iconURL = getClass().getResource("/images/popcorn.png");
        if (iconURL != null) {
            ImageIcon icon = new ImageIcon(iconURL);
            Image scaled = icon.getImage().getScaledInstance(130, 150, Image.SCALE_SMOOTH);
            logoLabel.setIcon(new ImageIcon(scaled));
        }

        titleRow.add(titleBox);
        titleRow.add(logoLabel);

        JLabel tagline = new JLabel("Sign in to discover what to watch next");
        tagline.setFont(new Font("SansSerif", Font.ITALIC, 24));
        tagline.setForeground(Color.LIGHT_GRAY);
        tagline.setAlignmentX(Component.CENTER_ALIGNMENT);

        headerPanel.add(titleRow);
        headerPanel.add(Box.createVerticalStrut(10));
        headerPanel.add(tagline);
        headerPanel.add(Box.createVerticalStrut(30));

        JPanel formPanel = new JPanel();
        formPanel.setBackground(darkBG);
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextField usernameField = new JTextField(15);
        usernameField.setMaximumSize(new Dimension(300, 40));
        usernameField.setFont(new Font("SansSerif", Font.PLAIN, 16));
        usernameField.setBorder(BorderFactory.createTitledBorder("Username"));

        JPasswordField passwordField = new JPasswordField(15);
        passwordField.setMaximumSize(new Dimension(300, 40));
        passwordField.setFont(new Font("SansSerif", Font.PLAIN, 16));
        passwordField.setBorder(BorderFactory.createTitledBorder("Password"));

        JButton loginButton = new JButton("Login");
        loginButton.setFont(new Font("SansSerif", Font.BOLD, 16));
        loginButton.setBackground(new Color(247, 179, 64));
        loginButton.setForeground(Color.BLACK);
        loginButton.setFocusPainted(false);
        loginButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        loginButton.setMaximumSize(new Dimension(120, 40));
        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton signUpButton = new JButton("Sign Up Here");
        signUpButton.setFont(new Font("SansSerif", Font.PLAIN, 14));
        signUpButton.setForeground(Color.LIGHT_GRAY);
        signUpButton.setBackground(new Color(30, 30, 30));
        signUpButton.setBorderPainted(false);
        signUpButton.setFocusPainted(false);
        signUpButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        signUpButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        signUpButton.addActionListener(e -> {
            dispose();
            new SignUp();
        });

        loginButton.addActionListener(e -> {
            String user = usernameField.getText().trim();
            String pass = new String(passwordField.getPassword());

            // quick hardcoded check
            if (user.equals("hi") && pass.equals("12345678")) {
                dispose();
                new StartWindow();
                return;
            }

            // run DB check in background
            new Thread(() -> {
                boolean valid = MyJDBC.isValidLogin(user, pass);
                int uid = MyJDBC.getUserId(user);

                SwingUtilities.invokeLater(() -> {
                    if (valid && uid != -1) {
                        User loggedInUser = new User(uid, user, pass);  // pass is available at that point
                        loggedInUser.setPlaylists(MyJDBC.getUserPlaylists(uid));
                        Session.setUser(loggedInUser);
                        dispose();
                        new StartWindow();
                    } else {
                        JOptionPane.showMessageDialog(this, "Invalid login.", "Login Failed", JOptionPane.ERROR_MESSAGE);
                    }
                });
            }).start();
        });

        formPanel.add(usernameField);
        formPanel.add(Box.createVerticalStrut(15));
        formPanel.add(passwordField);
        formPanel.add(Box.createVerticalStrut(25));
        formPanel.add(loginButton);
        formPanel.add(Box.createVerticalStrut(10));
        formPanel.add(signUpButton);

        wrapper.add(headerPanel);
        wrapper.add(formPanel);
        wrapper.add(Box.createVerticalGlue());

        add(wrapper, BorderLayout.CENTER);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Login::new);
    }
}
