package PopcornPicks.gui;

import PopcornPicks.model.Movie;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MovieGridPage extends JFrame {

    public MovieGridPage() {
        setTitle("Movie Results");
        setSize(900, 700);
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
        JPanel gridPanel = new JPanel(new GridLayout(0, 4, 20, 20));
        gridPanel.setBackground(new Color(20, 20, 20));
        gridPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        List<Movie> movies = getMoviesFromDB();
        for (Movie movie : movies) {
            gridPanel.add(createMovieBox(movie));
        }

        int rows = (int) Math.ceil(movies.size() / 4.0);
        gridPanel.setPreferredSize(new Dimension(860, rows * 300));

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
        panel.setBackground(new Color(30, 30, 30));
        panel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        panel.setPreferredSize(new Dimension(180, 280));
        panel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        ImageIcon iconNormal = null;
        ImageIcon iconHover = null;

        try {
            ImageIcon raw = new ImageIcon(movie.getPosterPath());
            Image image = raw.getImage();
            iconNormal = new ImageIcon(image.getScaledInstance(160, 240, Image.SCALE_SMOOTH));
            iconHover = new ImageIcon(image.getScaledInstance(200, 280, Image.SCALE_SMOOTH));
        } catch (Exception e) {
            System.err.println("Could not load image: " + movie.getPosterPath());
        }

        JLabel imageLabel = new JLabel(iconNormal);
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(imageLabel, BorderLayout.CENTER);

        JLabel titleLabel = new JLabel(movie.getTitle(), SwingConstants.CENTER);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(8, 4, 8, 4));
        panel.add(titleLabel, BorderLayout.SOUTH);

        ImageIcon finalIconNormal = iconNormal;
        ImageIcon finalIconHover = iconHover;

        panel.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                if (finalIconHover != null) imageLabel.setIcon(finalIconHover);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                if (finalIconNormal != null) imageLabel.setIcon(finalIconNormal);
            }

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
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://ambari-node5.csc.calpoly.edu:3306/?user=team2", "team2", "team2password");
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
        return movies;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MovieGridPage::new);
    }
}