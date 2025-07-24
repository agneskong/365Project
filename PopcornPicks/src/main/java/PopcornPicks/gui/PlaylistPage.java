package PopcornPicks.gui;

import PopcornPicks.model.Playlist;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.*;
import java.util.List;

public class PlaylistPage extends JFrame {
    public PlaylistPage(Playlist playlist) {
        setTitle(playlist.getName());
        setSize(1100, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(20, 20, 20));

        Color gold = new Color(247, 179, 64);
        Color light = Color.decode("#FEE6B6");
        Color darkBG = new Color(20, 20, 20);
        Color panelBG = new Color(28, 28, 28);

        // header
        JPanel topBar = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topBar.setBackground(new Color(20, 20, 20));

        // logo
        ImageIcon logoIcon = new ImageIcon(getClass().getResource("/images/popcorn.png"));
        Image logoImg = logoIcon.getImage().getScaledInstance(50, 60, Image.SCALE_SMOOTH);
        JButton logoButton = new JButton(new ImageIcon(logoImg));

        logoButton.setContentAreaFilled(false);
        logoButton.setBorderPainted(false);
        logoButton.setFocusPainted(false);
        logoButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        logoButton.addActionListener(e -> {
            dispose();
            new StartWindow();
        });

        // "Your Playlists" title
        JLabel titleLabel = new JLabel(playlist.getName());
        titleLabel.setFont(new Font("Georgia", Font.BOLD | Font.ITALIC, 48));
        titleLabel.setForeground(gold);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 300, 0, 0));

        topBar.add(logoButton);
        topBar.add(titleLabel);
        add(topBar, BorderLayout.NORTH);

        // main grid panel (grid style like MovieGridPage)
        JPanel gridPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 20));
        gridPanel.setBackground(darkBG);
        gridPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        List<String> movies = playlist.getTitles();
        if (movies.isEmpty()) {
            JLabel emptyLabel = new JLabel("This playlist is empty.");
            emptyLabel.setFont(new Font("SansSerif", Font.PLAIN, 20));
            emptyLabel.setForeground(Color.LIGHT_GRAY);
            emptyLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            gridPanel.add(emptyLabel);
        } else {
            for (String title : movies) {
                gridPanel.add(createPlaylistMovieBox(title));
            }
        }

        JScrollPane scrollPane = new JScrollPane(gridPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        add(scrollPane, BorderLayout.CENTER);


        setVisible(true);
    }

    // Add this method to create a card for each playlist item
    private JPanel createPlaylistMovieBox(String title) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setPreferredSize(new Dimension(140, 220)); // Fixed size like MovieGridPage
        panel.setBackground(new Color(30, 30, 30));
        panel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));

        // Use a default image for playlist items
        ImageIcon defaultIcon = new ImageIcon(getClass().getResource("/images/movie.png"));
        Image scaled = defaultIcon.getImage().getScaledInstance(120, 180, Image.SCALE_SMOOTH);
        JLabel imageLabel = new JLabel(new ImageIcon(scaled));
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(imageLabel, BorderLayout.CENTER);

        JLabel titleLabel = new JLabel("<html><center>" + title + "</center></html>", SwingConstants.CENTER);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(6, 4, 6, 4));
        panel.add(titleLabel, BorderLayout.SOUTH);

        return panel;
    }

    public static void main(String[] args) {
        Playlist test = new Playlist(1, "Test Playlist");
        test.getTitles().addAll(Arrays.asList("The Matrix", "Inception", "Arrival"));
        new PlaylistPage(test);
    }
}
