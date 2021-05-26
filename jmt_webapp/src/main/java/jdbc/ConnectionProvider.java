package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionProvider {
    private ConnectionProvider() { }
    /**
     * <p>
     * JDBC 커넥션 생성 및 connection 객체 제공
     * 
     * </p>
     * <strong>connection 사용 후, close 필수</strong>
     */
    public static Connection getJDBCConnection() {
        try {
            return DriverManager.getConnection("jdbc:apache:commons:dbcp:dbdbdev");
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
