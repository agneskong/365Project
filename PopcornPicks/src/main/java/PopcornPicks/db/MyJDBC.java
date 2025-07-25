package PopcornPicks.db;

import PopcornPicks.model.Movie;
import PopcornPicks.model.Playlist;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MyJDBC {
    private static final String URL = "jdbc:mysql://ambari-node5.csc.calpoly.edu:3306/team2";
    private static final String USER = "team2";
    private static final String PASS = "team2password";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }

    public static boolean isValidLogin(String username, String pwd) {
        String sql = "SELECT * FROM Users WHERE username = ? AND pwd = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, pwd);
            return stmt.executeQuery().next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static int getUserId(String username) {
        String sql = "SELECT uid FROM Users WHERE username = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return rs.getInt("uid");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static boolean registerUser(String username, String password) {
        String sql = "INSERT INTO Users (username, pwd, email) VALUES (?, ?, 'placeholder@example.com')";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static List<Playlist> getUserPlaylists(int uid) {
        List<Playlist> playlists = new ArrayList<>();
        String sql = "SELECT pname FROM Playlist WHERE uid = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, uid);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String pname = rs.getString("pname");
                playlists.add(new Playlist(uid, pname));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return playlists;
    }

    public static List<String> getPlaylistMovies(int uid, String pname) {
        List<String> titles = new ArrayList<>();
        String sql = """
            SELECT m.title
            FROM Movies m
            JOIN MoviePlaylist mp ON m.mid = mp.mid
            JOIN Playlist p ON mp.pid = p.pid
            WHERE p.uid = ? AND p.pname = ?
        """;
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, uid);
            stmt.setString(2, pname);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                titles.add(rs.getString("title"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return titles;
    }

    public static List<Movie> getFilteredMovies(List<String> genres, int yearFrom, int yearTo, double minRating) {
        List<Movie> results = new ArrayList<>();
        if (genres.isEmpty()) return results;

        // Build dynamic WHERE clause
        StringBuilder genrePlaceholders = new StringBuilder();
        for (int i = 0; i < genres.size(); i++) {
            genrePlaceholders.append("?");
            if (i < genres.size() - 1) genrePlaceholders.append(", ");
        }

        String sql = String.format("""
    SELECT m.title, g.genre, YEAR(m.date) AS year, m.rating, m.synopsis, m.poster
    FROM Movies m
    JOIN MovieGenres g ON m.mgid = g.mgid
    WHERE LOWER(g.genre) IN (%s)
      AND m.rating >= ?
      AND YEAR(m.date) BETWEEN ? AND ?
""", genrePlaceholders.toString());


        System.out.println("Filters: " + genres + " | Rating: " + minRating + " | Years: " + yearFrom + "-" + yearTo);

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            int index = 1;
            for (String genre : genres) {
                stmt.setString(index++, genre.toLowerCase());
            }
            stmt.setDouble(index++, minRating);
            stmt.setInt(index++, yearFrom);
            stmt.setInt(index, yearTo);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                results.add(new Movie(
                        rs.getString("title"),
                        rs.getString("genre"),
                        rs.getInt("year"),
                        rs.getFloat("rating"),
                        rs.getString("poster"),
                        rs.getString("synopsis")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return results;
    }
}
