package cz.safaricraft.plugin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;

public class DatabaseManager {

	private static Main plugin = Main.getInstance();
	/**
	 * @author FrostbiteCZ (Jason Skyedge)
	 */

	/**
	 * How many times will plugin try to connect to database?
	 */
	private static int attempsTillTimeout = 5;

	/**
	 * How many times have plugin tried to connect to database?
	 */
	private static int attemps = 0;

	/**
	 * Are we connected to the database?
	 */
	private static boolean connected = false;

	/**
	 * Holds connection to the database.
	 */
	private static Connection databaseConnection = null;

	/**
	 * Connets to SQL database.
	 * 
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public static void establishConnection() throws SQLException,
			ClassNotFoundException {
		attemps++;
		Main.logger.log(Level.INFO, "(attemp: " + attemps + ")");
		String driver = plugin.getConfig().getString("database.driver");
		String url = plugin.getConfig().getString("database.url");
		String login = plugin.getConfig().getString("database.login");
		String password = plugin.getConfig().getString("database.password");
		Class.forName(driver);
		databaseConnection = DriverManager.getConnection(url, login, password);
		connected = true;
		attemps = 0;
		Main.logger.log(Level.INFO,
				"Database connection established succesfuly!");
	}

	/**
	 * 
	 * @return Connection to the SQL database.
	 * @throws SQLException
	 *             If database connection wasn't initialized yet.
	 */
	public static Connection getConnection() throws SQLException {
		if (databaseConnection == null || !connected)
			throw new SQLException(
					"Database connection was not established succesfuly!");
		return databaseConnection;
	}

	/**
	 * Closes connection to SQL database.
	 * 
	 * @throws SQLException
	 */
	public static void disconnect() throws SQLException {
		connected = false;
		if (databaseConnection == null) {
			return;
		}
		databaseConnection.close();
	}

	/**
	 * @return True, if plugin is connected to the database, otherwise false.
	 */
	public static boolean connected() {
		return connected;
	}

	/**
	 * @return Times remaining till timeout.
	 */
	public static int getAttempsTillTimeout() {
		return attempsTillTimeout - attemps;
	}

}