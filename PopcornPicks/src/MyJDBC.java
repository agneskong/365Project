import java.sql.*;

public class MyJDBC {
    public static void main(String[] args) {
        try {
            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://ambari-node5.csc.calpoly.edu:3306/team2",
                    "team2",
                    "team2password"
            );

            Statement statment = connection.createStatement();
            ResultSet resultSet = statment.executeQuery("SELECT * FROM Users");

            while (resultSet.next()) {
                System.out.println(resultSet.getString("username"));
                System.out.println(resultSet.getString("pwd"));
            }
        } catch (SQLException e) {

        }
    }
}
