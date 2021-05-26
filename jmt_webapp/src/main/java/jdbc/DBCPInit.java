package jdbc;

import org.postgresql.core.ConnectionFactory;

public class DBCPInit extends HttpServlet{
    //web.xml 해결해야함.

    private static final long serialVersionUID = 1L;

	public DBCPInit() {
		super();
	}

	private void initConnectionPool() {

		try {
			String jdbcUrl = "";
			String username = "";
			String pw = "";

			ConnectionFactory connFactory = new DriverManagerConnectionFactory(jdbcUrl, username, pw);
			PoolableConnectionFactory poolableConnFactory = new PoolableConnectionFactory(connFactory, null);
			poolableConnFactory.setValidationQuery("select 1");

			GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
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
