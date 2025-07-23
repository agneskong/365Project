package PopcornPicks.gui;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class PosterDetailPage extends JFrame {

    public PosterDetailPage(String title, String genre, String posterPath, String synopsis) {
        setTitle("Movie Details");
        setSize(900, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(20, 20, 20));
        setLayout(new BorderLayout());

        // Header panel
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(20, 20, 20));
        header.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Popcorn icon (home button)
        URL iconURL = getClass().getResource("/images/popcorn.png");
        if (iconURL != null) {
            ImageIcon iconRaw = new ImageIcon(iconURL);
            Image iconImage = iconRaw.getImage().getScaledInstance(50, 60, Image.SCALE_SMOOTH);
            JLabel iconLabel = new JLabel(new ImageIcon(iconImage));
            iconLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            iconLabel.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent e) {
                    dispose();
                    new StartWindow(); // Navigate to StartWindow
                }
            });
            header.add(iconLabel, BorderLayout.WEST);
        } else {
            System.err.println("Could not load popcorn.png from /images");
        }

        // Title
        JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
        titleLabel.setFont(new Font("Georgia", Font.BOLD, 28));
        titleLabel.setForeground(new Color(247, 179, 64));
        header.add(titleLabel, BorderLayout.CENTER);
        add(header, BorderLayout.NORTH);

        // Center content
        JPanel content = new JPanel(new BorderLayout(30, 0));
        content.setBackground(new Color(20, 20, 20));
        content.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        // Poster image with border
        try {
            ImageIcon posterIcon = new ImageIcon(posterPath);
            Image posterImg = posterIcon.getImage().getScaledInstance(280, 400, Image.SCALE_SMOOTH);
            JLabel posterLabel = new JLabel(new ImageIcon(posterImg));

            // Wrap in a panel with padding and border
            JPanel posterWrapper = new JPanel(new BorderLayout());
            posterWrapper.setBackground(new Color(20, 20, 20));
            posterWrapper.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(Color.GRAY, 2), // Outer visible border
                    BorderFactory.createEmptyBorder(150, 150, 150, 150) // Inner padding
            ));
            posterWrapper.add(posterLabel, BorderLayout.CENTER);

            content.add(posterWrapper, BorderLayout.WEST);

        } catch (Exception e) {
            JLabel errorLabel = new JLabel("Poster not found", SwingConstants.CENTER);
            errorLabel.setForeground(Color.RED);
            content.add(errorLabel, BorderLayout.WEST);
        }

        // Detail section
        JPanel detailPanel = new JPanel();
        detailPanel.setLayout(new BoxLayout(detailPanel, BoxLayout.Y_AXIS));
        detailPanel.setBackground(new Color(20, 20, 20));

        JLabel genreLabel = new JLabel("Genre: " + genre);
        genreLabel.setForeground(Color.WHITE);
        genreLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
        genreLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JTextArea synopsisArea = new JTextArea(synopsis);
        synopsisArea.setLineWrap(true);
        synopsisArea.setWrapStyleWord(true);
        synopsisArea.setEditable(false);
        synopsisArea.setFont(new Font("SansSerif", Font.PLAIN, 14));
        synopsisArea.setBackground(new Color(30, 30, 30));
        synopsisArea.setForeground(Color.WHITE);
        synopsisArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JScrollPane synopsisScroll = new JScrollPane(synopsisArea);
        synopsisScroll.setPreferredSize(new Dimension(400, 300));
        synopsisScroll.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        synopsisScroll.setAlignmentX(Component.LEFT_ALIGNMENT);
        synopsisScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        detailPanel.add(genreLabel);
        detailPanel.add(Box.createVerticalStrut(15));
        detailPanel.add(synopsisScroll);

        content.add(detailPanel, BorderLayout.CENTER);
        add(content, BorderLayout.CENTER);

        // Footer
        JPanel footer = new JPanel();
        footer.setBackground(new Color(20, 20, 20));
        JButton backButton = new JButton("← Back");
        backButton.setBackground(new Color(40, 40, 40));
        backButton.setForeground(Color.WHITE);
        backButton.setFocusPainted(false);
        backButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        backButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        backButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        backButton.addActionListener(e -> {
            dispose();
            new MovieGridPage();
        });

        footer.add(backButton);
        add(footer, BorderLayout.SOUTH);

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PosterDetailPage(
                "Inception",
                "Sci-Fi, Thriller",
                "images/inception.jpg",
                "A skilled thief who steals corporate secrets through dream-sharing technology is given a chance at redemption..."
        ));
    }
}
