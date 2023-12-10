package englishlearningapp.englearning.JDBCConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBC_Connect {
    protected static Connection getJDBCConnection ()  throws SQLException {
        final String url = "jdbc:mysql://localhost:3306/dictionary";
        final String user = "root";
        final String password = "";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
       return DriverManager.getConnection(url, user, password);
    }
    public static void main(String[] args) throws SQLException {
        Connection connection = getJDBCConnection();
        if(connection != null) {
            System.out.println("thanh cong");
            System.out.println(connection);
        }else {
            System.out.println("that bai");
            System.out.println(connection);

        }
    }
}
