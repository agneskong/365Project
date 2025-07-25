package PopcornPicks.gui;

import javax.swing.*;
import java.awt.*;

public class StartWindow extends JFrame {

    public StartWindow() {
        setTitle("Popcorn Picks");
        setSize(1200, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        Color darkBG = new Color(20, 20, 20);
        getContentPane().setBackground(new Color(20, 20, 20));
        setLayout(new BorderLayout());

        // header
        JPanel headerContainer = new JPanel();
        headerContainer.setLayout(new BoxLayout(headerContainer, BoxLayout.Y_AXIS));
        headerContainer.setBackground(darkBG);

        // title row
        JPanel titleBox = new JPanel();
        titleBox.setLayout(new BoxLayout(titleBox, BoxLayout.Y_AXIS));
        titleBox.setBackground(darkBG);

        // “Popcorn”
        JLabel popcornLabel = new JLabel("Popcorn");
        popcornLabel.setForeground(new Color(247, 179, 64));
        popcornLabel.setFont(new Font("Georgia", Font.BOLD | Font.ITALIC, 72));
        popcornLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // “Picks”
        JLabel picksLabel = new JLabel("Picks");
        picksLabel.setForeground(new Color(247, 179, 64));
        picksLabel.setFont(new Font("Georgia", Font.BOLD | Font.ITALIC, 72));
        picksLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        picksLabel.setBorder(BorderFactory.createEmptyBorder(0, 170, 0, 0)); // indent to the right

        titleBox.add(popcornLabel);
        titleBox.add(picksLabel);

        // popcorn logo
        ImageIcon logoIcon = new ImageIcon(getClass().getResource("/images/popcorn.png"));
        Image logoImg = logoIcon.getImage().getScaledInstance(130, 150, Image.SCALE_SMOOTH);
        JLabel logoLabel = new JLabel(new ImageIcon(logoImg));

        // title row with logo
        JPanel titleRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        titleRow.setBackground(darkBG);
        titleRow.add(titleBox);
        titleRow.add(logoLabel);

        // tagline
        JLabel tagline = new JLabel("Discover what to watch next");
        tagline.setFont(new Font("SansSerif", Font.ITALIC, 24));
        tagline.setForeground(Color.LIGHT_GRAY);
        tagline.setAlignmentX(Component.CENTER_ALIGNMENT);

        // final header assembly
        headerContainer.add(titleRow);
        headerContainer.add(Box.createVerticalStrut(5));
        headerContainer.add(tagline);
        headerContainer.add(Box.createVerticalStrut(15));

        add(headerContainer, BorderLayout.NORTH);

        // center buttons
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 60, 0));
        centerPanel.setBackground(darkBG);

        JPanel moviePanel = createImageButtonPanel("movie.png", "Movies", () -> {
            dispose();
            new FilterPage();
        });

        JPanel tvPanel = createImageButtonPanel("tv.png", "TV Shows", () -> {
            dispose();
            new FilterPage();
        });

        JPanel playlistPanel = createImageButtonPanel("playlist.png", "Playlist", () -> {
            dispose();
            new PlaylistHomePage();
        });

        centerPanel.add(moviePanel);
        centerPanel.add(tvPanel);
        centerPanel.add(playlistPanel);

        add(centerPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    private JPanel createImageButtonPanel(String imageFile, String labelText, Runnable onClick) {
        Color panelBG = new Color(28, 28, 28); // rounded background
        Color labelColor = Color.WHITE;
        Color hoverHighlight = new Color(255, 239, 184); // soft glow color

        // rounded background
        RoundedPanel rounded = new RoundedPanel(30, panelBG);
        rounded.setLayout(new BoxLayout(rounded, BoxLayout.Y_AXIS));
        rounded.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        rounded.setAlignmentX(Component.CENTER_ALIGNMENT);

        // loads and scales image
        ImageIcon iconRaw = new ImageIcon(getClass().getResource("/images/" + imageFile));
        Image baseImage = iconRaw.getImage().getScaledInstance(230, 220, Image.SCALE_SMOOTH);
        ImageIcon baseIcon = new ImageIcon(baseImage);

        JButton button = new JButton(baseIcon);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.addActionListener(e -> onClick.run());

        // hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                Image hoverImg = iconRaw.getImage().getScaledInstance(240, 230, Image.SCALE_SMOOTH);
                button.setIcon(new ImageIcon(hoverImg));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setIcon(baseIcon);
            }
        });

        // label
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("SansSerif", Font.BOLD, 16));
        label.setForeground(labelColor);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);

        // assemble
        rounded.add(button);
        rounded.add(Box.createVerticalStrut(10));
        rounded.add(label);

        return rounded;
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(StartWindow::new);
    }
}
