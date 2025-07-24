package PopcornPicks.gui;

import PopcornPicks.model.Playlist;
import PopcornPicks.model.Session;
import PopcornPicks.model.User;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class PlaylistHomePage extends JFrame {

    public PlaylistHomePage() {
        setTitle("Playlists");
        setSize(1100, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(20, 20, 20));

        Color gold = new Color(247, 179, 64);
        Color darkBG = new Color(20, 20, 20);
        Color light = Color.decode("#FEE6B6");

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
        JLabel titleLabel = new JLabel("Your Playlists");
        titleLabel.setFont(new Font("Georgia", Font.BOLD | Font.ITALIC, 48));
        titleLabel.setForeground(gold);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 300, 0, 0));

        topBar.add(logoButton);
        topBar.add(titleLabel);
        add(topBar, BorderLayout.NORTH);

        // Main panel (vertical layout)
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(darkBG);

        // "New Playlist" button
        // JLabel newPlaylist = new JLabel(\);
        JButton newPlaylistButton = makeStyledButton("➕ New Playlist");
        newPlaylistButton.addActionListener(e -> {
            // Logic to open a create playlist window
            System.out.println("Create New Playlist");
        });
        newPlaylistButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(newPlaylistButton);
        mainPanel.add(Box.createVerticalStrut(30));

        // Grid of saved playlists
        JPanel gridPanel = new JPanel(new GridLayout(0, 2, 30, 30)); // 2 per row
        gridPanel.setBackground(darkBG);

        User currentUser = Session.getUser();
        List<Playlist> playlists = currentUser.getPlaylists();

        for (Playlist playlist : playlists) {
            JButton playlistButton = makeStyledButton(playlist.getName());
            playlistButton.addActionListener(e -> {
                System.out.println("Open Playlist: " + playlist.getName());
                new PlaylistPage(playlist);
            });
            gridPanel.add(playlistButton);
        }

        JPanel gridWrapper = new JPanel(new BorderLayout());
        gridWrapper.setBackground(darkBG);
        gridWrapper.setBorder(BorderFactory.createEmptyBorder(0, 60, 0, 60));
        gridWrapper.add(gridPanel, BorderLayout.CENTER);

        mainPanel.add(gridWrapper);

        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        add(scrollPane, BorderLayout.CENTER);
        setVisible(true);
    }

    private JButton makeStyledButton(String label) {
        JButton button = new JButton(label);
        button.setFont(new Font("SansSerif", Font.BOLD, 22));
        button.setBackground(Color.decode("#FEE6B6"));
        button.setForeground(Color.white);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() ->
                new PlaylistHomePage()
        );
    }
}
