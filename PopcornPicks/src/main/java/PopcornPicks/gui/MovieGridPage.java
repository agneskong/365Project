package PopcornPicks.gui;

import PopcornPicks.model.Movie;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MovieGridPage extends JFrame {

    public MovieGridPage() {
        setTitle("Movie Results");
        setSize(1100, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(20, 20, 20));
        setLayout(new BorderLayout());

        // ---------- HEADER PANEL ----------
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(20, 20, 20));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

        JLabel popcornIcon = createPopcornIcon();
        if (popcornIcon != null) {
            popcornIcon.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            popcornIcon.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseClicked(java.awt.event.MouseEvent e) {
                    dispose();
                    new StartWindow();
                }
            });
            headerPanel.add(popcornIcon, BorderLayout.WEST);
        }

        JLabel titleLabel = new JLabel("Your Picks", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Georgia", Font.BOLD, 32));
        titleLabel.setForeground(new Color(247, 179, 64));
        headerPanel.add(titleLabel, BorderLayout.CENTER);

        add(headerPanel, BorderLayout.NORTH);

        // ---------- GRID PANEL ----------
        JPanel gridPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 20));
        gridPanel.setBackground(new Color(20, 20, 20));
        gridPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        List<Movie> movies = getMoviesFromDB();
        for (Movie movie : movies) {
            gridPanel.add(createMovieBox(movie));
        }

        JScrollPane scrollPane = new JScrollPane(gridPanel,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setBorder(null);

        add(scrollPane, BorderLayout.CENTER);
        setVisible(true);
    }

        private JLabel createPopcornIcon() {
        URL url = getClass().getResource("/images/popcorn.png");
        if (url != null) {
            ImageIcon icon = new ImageIcon(url);
            Image scaled = icon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
            return new JLabel(new ImageIcon(scaled));
        } else {
            System.err.println("Could not load popcorn.png from /images");
            return null;
        }
    }

    private JPanel createMovieBox(Movie movie) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setPreferredSize(new Dimension(140, 220)); // Fixed size!
        panel.setBackground(new Color(30, 30, 30));
        panel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));

        ImageIcon posterIcon;
        try {
            ImageIcon raw = new ImageIcon(movie.getPosterPath());
            Image scaled = raw.getImage().getScaledInstance(120, 180, Image.SCALE_SMOOTH);
            posterIcon = new ImageIcon(scaled);
        } catch (Exception e) {
            posterIcon = new ImageIcon(new BufferedImage(120, 180, BufferedImage.TYPE_INT_RGB));
        }

        JLabel imageLabel = new JLabel(posterIcon);
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(imageLabel, BorderLayout.CENTER);

        JLabel titleLabel = new JLabel("<html><center>" + movie.getTitle() + "</center></html>", SwingConstants.CENTER);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(6, 4, 6, 4));
        panel.add(titleLabel, BorderLayout.SOUTH);

        panel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        panel.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                dispose();
                new PosterDetailPage(movie);
            }
        });

        return panel;
    }

    private List<Movie> getMoviesFromDB() {
        List<Movie> movies = new ArrayList<>();

        // DUMMY MOVIES FOR FILLER
        movies.add(new Movie("Interstellar", "Sci-Fi", 2014, 8.6f,
                "A team of explorers travel through a wormhole in space in an attempt to ensure humanity's survival.",
                "images/interstellar.jpg"));
        movies.add(new Movie("The Dark Knight", "Action", 2008, 9.0f,
                "Batman faces the Joker, a criminal mastermind bent on chaos in Gotham.",
                "images/dark_knight.jpg"));
        movies.add(new Movie("Spirited Away", "Animation", 2001, 8.6f,
                "A young girl enters a mysterious world of spirits and must find a way to rescue her parents.",
                "images/spirited_away.jpg"));
        movies.add(new Movie("Parasite", "Thriller", 2019, 8.6f,
                "A poor family schemes to become employed by a wealthy household by posing as unrelated professionals.",
                "images/parasite.jpg"));

        // Uncomment to test DB connection later
    /*
    try (Connection conn = DriverManager.getConnection("jdbc:mysql://ambari-node5.csc.calpoly.edu:3306/team2",
            "team2", "team2password");
         Statement stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery("SELECT title, genre, year, rating, synopsis, posterPath FROM Movies")) {

        while (rs.next()) {
            String title = rs.getString("title");
            String genre = rs.getString("genre");
            int year = rs.getInt("year");
            float rating = rs.getFloat("rating");
            String synopsis = rs.getString("synopsis");
            String posterPath = rs.getString("posterPath");

            movies.add(new Movie(title, genre, year, rating, synopsis, posterPath));
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }
    */

        return movies;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MovieGridPage::new);
    }
}