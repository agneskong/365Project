package PopcornPicks.gui;

import PopcornPicks.db.MyJDBC;
import PopcornPicks.model.Playlist;
import PopcornPicks.model.Session;
import PopcornPicks.model.User;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class PlaylistHomePage extends JFrame {

    public PlaylistHomePage() {
        setTitle("Your Playlists");
        setSize(1100, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(20, 20, 20));

        Color gold = new Color(247, 179, 64);
        Color darkBG = new Color(20, 20, 20);
        Color light = Color.decode("#FEE6B6");

        // ---------- HEADER ----------
        JPanel topBar = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topBar.setBackground(darkBG);

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

        JLabel titleLabel = new JLabel("Your Playlists");
        titleLabel.setFont(new Font("Georgia", Font.BOLD | Font.ITALIC, 48));
        titleLabel.setForeground(gold);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 300, 0, 0));

        topBar.add(logoButton);
        topBar.add(titleLabel);
        add(topBar, BorderLayout.NORTH);

        // ---------- MAIN PANEL ----------
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(darkBG);

        // "New Playlist" button (placeholder logic)
        JButton newPlaylistButton = makeStyledButton("➕ New Playlist");
        newPlaylistButton.addActionListener(e -> {
            // You can open a dialog or create playlist logic here
            JOptionPane.showMessageDialog(this, "Create Playlist functionality not implemented.");
        });
        newPlaylistButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(newPlaylistButton);
        mainPanel.add(Box.createVerticalStrut(30));

        // ---------- FETCH USER & PLAYLISTS ----------
        /*User currentUser = Session.getUser();
        if (currentUser == null) {
            JOptionPane.showMessageDialog(this, "Error: no user session found.", "Session Error", JOptionPane.ERROR_MESSAGE);
            dispose();
            new Login();
            return;
        }

        int uid = currentUser.getUid();
        List<Playlist> playlists = MyJDBC.getUserPlaylists(uid);
        currentUser.setPlaylists(playlists);*/
        // Always use hardcoded demo playlists
        java.util.List<Playlist> playlists = new java.util.ArrayList<>();
        Playlist p1 = new Playlist(1, "Favorites");
        Playlist p2 = new Playlist(2, "Watch Later");
        Playlist p3 = new Playlist(3, "Comedies");
        playlists.add(p1);
        playlists.add(p2);

        // ---------- PLAYLIST GRID ----------
        JPanel gridPanel = new JPanel(new GridLayout(0, 2, 30, 30)); // 2 columns
        gridPanel.setBackground(darkBG);

        if (playlists.isEmpty()) {
            JLabel emptyMsg = new JLabel("You don’t have any playlists yet.");
            emptyMsg.setFont(new Font("SansSerif", Font.ITALIC, 18));
            emptyMsg.setForeground(Color.LIGHT_GRAY);
            emptyMsg.setAlignmentX(Component.CENTER_ALIGNMENT);
            mainPanel.add(emptyMsg);
        } else {
            for (Playlist playlist : playlists) {
                JButton playlistButton = makeStyledButton(playlist.getName());
                playlistButton.addActionListener(e -> {
                    dispose();
                    //new PlaylistPage(playlist);
                    // Always use hardcoded movies for demo
                    Playlist demoPlaylist = new Playlist(playlist.getName().hashCode(), playlist.getName());
                    demoPlaylist.getTitles().add("The Matrix");
                    demoPlaylist.getTitles().add("Inception");
                    demoPlaylist.getTitles().add("Arrival");
                    demoPlaylist.getTitles().add("The Dark Knight");
                    new PlaylistPage(demoPlaylist);
                });
                gridPanel.add(playlistButton);
            }
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
        SwingUtilities.invokeLater(PlaylistHomePage::new);
    }
}
