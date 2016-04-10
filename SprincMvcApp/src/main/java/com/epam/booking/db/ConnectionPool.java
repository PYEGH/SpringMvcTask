package com.epam.booking.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component()
@Scope("singleton")
public class ConnectionPool {

	private final Logger logger = Logger.getLogger(ConnectionPool.class);

	private BlockingQueue<Connection> connectionQueue;
	private static final String WARN1 = "Database access error";
	private static final String WARN2 = "Returning connection not added. Possible `leakage` of connections";
	private static final String WARN3 = "Trying to return closed connection. Possible `leakage` of connections";
	private static final String WARN4 = "SQL exception.";

	private static final String POOL_SIZE = "3";
	private static final String CLASS_DRIVER = "oracle.jdbc.OracleDriver";
	private static final String URL = "jdbc:oracle:thin:@localhost:1521:XE";
	private static final String USER = "SYSTEM";
	private static final String PASSWORD = "abc123";

	@Autowired
	public ConnectionPool(@Value(POOL_SIZE) int poolSize, @Value(URL) String url,
			@Value(USER) String user, @Value(PASSWORD) String pass)
			throws SQLException, ClassNotFoundException {

		Class.forName("oracle.jdbc.OracleDriver");
		connectionQueue = new ArrayBlockingQueue<Connection>(poolSize);
		Connection conn;
		try {
			for (int i = 0; i < poolSize; i++) {

				conn = DriverManager.getConnection(url, user, pass);
				connectionQueue.offer(conn);
			}
		} catch (Exception ex) {
			logger.error("Error during creation", ex);

		}
	}

	public Connection getConnection() throws InterruptedException {
		Connection connection = null;
		connection = connectionQueue.take();
		return connection;
	}

	public void closeConnection(Connection connection) throws SQLException {
		boolean closed = true;
		try {
			closed = connection.isClosed();
		} catch (SQLException e) {
			logger.warn(WARN1, e);
		}
		if (!closed) {
			if (!connectionQueue.offer(connection)) {
				connection.close();//
				logger.warn(WARN2);
			}
		} else {
			connection.close();//
			logger.warn(WARN3);
		}
	}

	public void dispose() {
		Connection connection;
		while ((connection = connectionQueue.poll()) != null) {
			try {
				if (!connection.getAutoCommit()) {
					connection.commit();
				}
				connection.close();
			} catch (SQLException e) {
				logger.warn(WARN4, e);
			}
		}
	}

	@PostConstruct
	public void init() throws Exception {
		System.out
				.println("Now init method for ConnectionPool is working. It does nothig just outputs this message to show callback usage.  ");
	}

	@PreDestroy
	public void cleanUp() throws Exception {
		System.out
				.println("Now cleanUp method for ConnectionPool is working. Created for the same goal that init method: jsut to show callback usage");
	}
}