package de.xgme.webcms;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * An object of this type contains the data which is used to handle an
 * request and generate the response.
 * Be aware that this class is not thread-safe.
 * If you use the object only in context of request handling for the request
 * for which the object was created and you are not using own threads,
 * all should work without synchronization.
 */
public abstract class QueryProcessingBase {
	private final WebCMS cms;
	private Connection conn = null;

	QueryProcessingBase(WebCMS cms) {
		this.cms = cms;
	}

	/**
	 * Gets the request of this handling process.
	 * @return The Request
	 */
	public abstract Request getRequest();

	/**
	 * Gets the response which will be sent to the client.
	 * @return The Response
	 */
	public abstract Response getResponse();

	/**
	 * Gets the the instance of the CMS.
	 * @return The CMS
	 */
	public WebCMS getCms() {
		return cms;
	}

	/**
	 * Gets a connection to the database which is specified in settings of the CMS.
	 * @return A connection to the database
	 * @throws SQLException if an SQL error occurred
	 */
	public Connection getDBConnection() throws SQLException {
		if (conn == null) {
			conn = cms.getDBConnection();
		}
		return conn;
	}

	/**
	 * This method is should be called at the end of process.
	 * It is important to call super.finish() If someone overrides this method.
	 * @throws IOException if an IO error occurred
	 */
	void finish() throws IOException {
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO write warning in log
			}
		}
	}

}
