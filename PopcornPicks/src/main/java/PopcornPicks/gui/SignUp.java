package PopcornPicks.gui;

import PopcornPicks.db.MyJDBC;

import javax.swing.*;
import java.awt.*;

public class SignUp extends JFrame {

    public SignUp() {
        setTitle("Popcorn Picks Sign Up");
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

        // ---------- HEADER ----------
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
        ImageIcon icon = new ImageIcon(getClass().getResource("/images/popcorn.png"));
        Image scaled = icon.getImage().getScaledInstance(130, 150, Image.SCALE_SMOOTH);
        logoLabel.setIcon(new ImageIcon(scaled));

        titleRow.add(titleBox);
        titleRow.add(logoLabel);

        JLabel tagline = new JLabel("Register for Popcorn Picks");
        tagline.setFont(new Font("SansSerif", Font.ITALIC, 24));
        tagline.setForeground(Color.LIGHT_GRAY);
        tagline.setAlignmentX(Component.CENTER_ALIGNMENT);

        headerPanel.add(titleRow);
        headerPanel.add(Box.createVerticalStrut(10));
        headerPanel.add(tagline);
        headerPanel.add(Box.createVerticalStrut(30));

        // ---------- FORM ----------
        JPanel formPanel = new JPanel();
        formPanel.setBackground(darkBG);
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextField usernameField = new JTextField(15);
        usernameField.setMaximumSize(new Dimension(300, 40));
        usernameField.setFont(new Font("SansSerif", Font.PLAIN, 16));
        usernameField.setBorder(BorderFactory.createTitledBorder("Create Username"));

        JPasswordField passwordField = new JPasswordField(15);
        passwordField.setMaximumSize(new Dimension(300, 40));
        passwordField.setFont(new Font("SansSerif", Font.PLAIN, 16));
        passwordField.setBorder(BorderFactory.createTitledBorder("Create Password"));

        JButton signupButton = new JButton("Sign Up");
        signupButton.setFont(new Font("SansSerif", Font.BOLD, 16));
        signupButton.setBackground(new Color(247, 179, 64));
        signupButton.setForeground(Color.BLACK);
        signupButton.setFocusPainted(false);
        signupButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        signupButton.setMaximumSize(new Dimension(120, 40));
        signupButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton loginButton = new JButton("Back to Login");
        loginButton.setFont(new Font("SansSerif", Font.PLAIN, 14));
        loginButton.setForeground(Color.LIGHT_GRAY);
        loginButton.setBackground(new Color(30, 30, 30));
        loginButton.setBorderPainted(false);
        loginButton.setFocusPainted(false);
        loginButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginButton.addActionListener(e -> {
            dispose();
            new Login();
        });

        signupButton.addActionListener(e -> {
            String user = usernameField.getText().trim();
            String pass = new String(passwordField.getPassword()).trim();

            if (user.isEmpty() || pass.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Username and password cannot be empty.", "Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (pass.length() < 8) {
                JOptionPane.showMessageDialog(this, "Password must be at least 8 characters long.", "Invalid Password", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (MyJDBC.getUserId(user) != -1) {
                JOptionPane.showMessageDialog(this, "Username already exists. Please choose a different one.", "Duplicate Username", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (MyJDBC.registerUser(user, pass)) {
                JOptionPane.showMessageDialog(this, "Sign up successful! Please log in.");
                dispose();
                new Login();
            } else {
                JOptionPane.showMessageDialog(this, "Error during sign up. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        formPanel.add(usernameField);
        formPanel.add(Box.createVerticalStrut(15));
        formPanel.add(passwordField);
        formPanel.add(Box.createVerticalStrut(25));
        formPanel.add(signupButton);
        formPanel.add(Box.createVerticalStrut(10));
        formPanel.add(loginButton);

        wrapper.add(headerPanel);
        wrapper.add(formPanel);
        wrapper.add(Box.createVerticalGlue());
        add(wrapper, BorderLayout.CENTER);

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SignUp::new);
    }
}
