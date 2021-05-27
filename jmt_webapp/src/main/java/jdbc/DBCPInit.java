package jdbc;

import java.sql.DriverManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.commons.dbcp2.ConnectionFactory;
import org.apache.commons.dbcp2.DriverManagerConnectionFactory;
import org.apache.commons.dbcp2.PoolableConnection;
import org.apache.commons.dbcp2.PoolableConnectionFactory;
import org.apache.commons.dbcp2.PoolingDriver;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

public class DBCPInit extends HttpServlet {
    // TODO web.xml 해결해야함.

    private static final long serialVersionUID = 1L;

    public DBCPInit() {
        super();
    }

    @Override
    public void init() throws ServletException {

        initConnectionPool();
    }

    @SuppressWarnings({"unchecked"})
    private void initConnectionPool() {

        try {
            String jdbcUrl = "";
            String username = "";
            String pw = "";

            ConnectionFactory connFactory = new DriverManagerConnectionFactory(jdbcUrl, username, pw);
            PoolableConnectionFactory poolableConnFactory = new PoolableConnectionFactory(connFactory, null);
            poolableConnFactory.setValidationQuery("select 1");

            GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig<>();
            poolConfig.setTimeBetweenEvictionRunsMillis(1000L * 60L * 5L);
            poolConfig.setTestWhileIdle(true);
            poolConfig.setMinIdle(4);
            poolConfig.setMaxTotal(50);

            GenericObjectPool<PoolableConnection> connectionPool = new GenericObjectPool<PoolableConnection>(
                    poolableConnFactory, poolConfig);
            poolableConnFactory.setPool(connectionPool);

            Class.forName("org.apache.commons.dbcp2.PoolingDriver");
            PoolingDriver driver = (PoolingDriver) DriverManager.getDriver("jdbc:apache:commons:dbcp:");
            driver.registerPool("dbdbdev", connectionPool);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

}
