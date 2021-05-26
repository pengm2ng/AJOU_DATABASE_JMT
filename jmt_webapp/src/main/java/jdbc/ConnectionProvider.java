package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionProvider {
    /**
     * JDBC 커넥션 생성 및 제공
     */
    public static Connection getJDBCConnection() {
        try {
            return DriverManager.getConnection("jdbc:apache:commons:dbcp:dbdbdev");
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
}
