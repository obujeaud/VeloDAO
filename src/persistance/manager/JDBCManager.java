package persistance.manager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class JDBCManager {

	private static JDBCManager INSTANCE = null;
	private static Connection connection;
	private Properties prop = new Properties();

	private JDBCManager() {
	}

	public static JDBCManager getInstance() {
		if (INSTANCE == null)
			INSTANCE = new JDBCManager();

		return INSTANCE;
	}

	public Connection openConection() throws Exception {
		prop = CfgManager.getInstance().getCfg();
		Class.forName(prop.getProperty("driverName"));
		connection = DriverManager.getConnection(prop.getProperty("url"), prop.getProperty("user"),
				prop.getProperty("password"));

		return connection;
	}

	public void closeConnection() throws SQLException {
		connection.close();
	}
}