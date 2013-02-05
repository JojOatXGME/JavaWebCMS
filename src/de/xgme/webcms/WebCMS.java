package de.xgme.webcms;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.SessionCookieConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public final class WebCMS {

	private final ServletConfig servletConfig;
	private final File configDir;

	private Settings settings;
	private Logger logger;

	private Connection sqlConnection = null;
	private final Object sqlLock = new Object();

	/**
	 * Gets the logger of the CMS.
	 * @return The Logger
	 */
	public Logger getLogger() {
		return logger;
	}

	/**
	 * Gets an Connection to the database which is specified in settings of CMS.
	 * @return A Connection to the database
	 * @throws SQLException if a SQL error occurred
	 */
	public Connection getDBConnection() throws SQLException {
		Settings s = this.settings;
		
		if (s.getSqlSingleConnection()) {
			synchronized (sqlLock) {
				int timeout = 0;
				// TODO int timeout = (int) TimeUnit.SECONDS.convert(sqlConnection.getNetworkTimeout(), TimeUnit.MILLISECONDS);
				if (sqlConnection == null || sqlConnection.isValid(timeout)) {
					sqlConnection = getDBConnection(s.getSqlDriver(),
							s.getSqlUrl(), s.getSqlUser(), s.getSqlPasswd());
				}
				return sqlConnection;
			}
		} else {
			return getDBConnection(s.getSqlDriver(), s.getSqlUrl(),
					s.getSqlUser(), s.getSqlPasswd());
		}
	}

	/**
	 * Updates the settings from configuration file.
	 * @throws FileNotFoundException if the configuration file can not be read
	 * @throws InvalidConfigurationException if the configuration file is not valid
	 */
	void updateSettings() throws FileNotFoundException, InvalidConfigurationException {
		Reader reader = new FileReader(new File(configDir, "settings.yml"));
		settings = new Settings(reader);
		
		updateLogger();
		
		try {
			reader.close();
		} catch (IOException e) {
			// TODO write warning in log
		}
	}

	/**
	 * Updates the logger to match the configuration.
	 */
	void updateLogger() {
		Settings s = settings;
		Logger newLog = Logger.getLogger(s.getLoggerName());
		
		if (newLog != logger) {
			if (!s.getLogfile().equalsIgnoreCase("none")) {
				try {
					FileHandler handler = new FileHandler(s.getLogfile(), 1024*1024*10, 3);
					newLog.addHandler(handler);
				} catch (SecurityException | IOException e) {
					// TODO write warning in log
				}
			}
			if (!s.getLogfileWarning().equalsIgnoreCase("none")) {
				try {
					FileHandler handler = new FileHandler(s.getLogfileWarning(), 1024*1024*10, 1);
					newLog.addHandler(handler);
					handler.setLevel(Level.WARNING);
				} catch (SecurityException | IOException e) {
					// TODO write warning in log
				}
			}
			if (!s.getLogfileSevere().equalsIgnoreCase("none")) {
				try {
					FileHandler handler = new FileHandler(s.getLogfileSevere(), 1024*1024*10, 1);
					newLog.addHandler(handler);
					handler.setLevel(Level.SEVERE);
				} catch (SecurityException | IOException e) {
					// TODO write warning in log
				}
			}
		}
		
		// TODO set up logger (maybe more then one process? -> more then one file)
		// TODO in debug mode also use DriverManager.setLogWriter(PrintWriter)
		
		logger = newLog;
	}

	/**
	 * Handle an GET request.
	 * @param servlRequest The request from client
	 * @param servlResponse The response which will send to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an IO error occurred
	 */
	void doGet(HttpServletRequest servlRequest, HttpServletResponse servlResponse)
			throws ServletException, IOException {
		
		// TODO handle get request (use HttpStatus.METHOD when there is no page but a post request handler)
	}

	/**
	 * Handle an POST request.
	 * @param servlRequest The request from client
	 * @param servlResponse The response which will send to client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an IO error occurred
	 */
	void doPost(HttpServletRequest servlRequest, HttpServletResponse servlResponse)
			throws ServletException, IOException {
		
		PostProcessingBase processingBase = new PostProcessingBase(servlRequest, servlResponse, this);
		// TODO handle post request (use HttpStatus.METHOD when there is no handler but there is a normal page)
		
		processingBase.finish();
	}

	private Connection getDBConnection(final String driver,
			final String url, final String user, final String passwd)
					throws SQLException {
		
		if (driver != null) {
			try {
				Class.forName(driver);
			} catch (ClassNotFoundException e) {
				// TODO write warning in log
			}
		}
		
		Connection con = DriverManager.getConnection(url, user, passwd);
		// TODO set up TypeMap?
		return con;
	}

	private WebCMS(ServletConfig servletConfig) throws ServletException {
		this.servletConfig = servletConfig;
		this.logger = Logger.getLogger(getClass().getSimpleName());
		
		configDir = new File(
				servletConfig.getServletContext().getRealPath("/config") // TODO deny access to this directory
				);
		
		try {
			updateSettings();
		} catch (Exception e) {
			throw new ServletException("Unable to load settings", e);
		}
		logger.finer("Configuration loaded and logger is ready");
		
		// TODO set up other thinks
		
		// set up session configuration
		logger.fine("Set up session cookie configuration ...");
		SessionCookieConfig sessionConfig = servletConfig.getServletContext().getSessionCookieConfig();
		if (!settings.getSessionName().equalsIgnoreCase("default")) {
			sessionConfig.setName(settings.getSessionName());
		}
		if (!settings.getSessionDomain().equalsIgnoreCase("default")) {
			sessionConfig.setDomain(settings.getSessionDomain());
		}
		if (!settings.getSessionPath().equalsIgnoreCase("default")) {
			sessionConfig.setPath(settings.getSessionPath());
		}
		if (settings.getSessionMaxAge() != null) {
			sessionConfig.setMaxAge(settings.getSessionMaxAge());
		}
		sessionConfig.setHttpOnly(settings.isSessionHttpOnly());
		sessionConfig.setSecure(settings.isSessionSecure());
		
		// TODO set up plug-in manager
		logger.fine("Set up plug-in manager ...");
		File pluginDir = new File(configDir, "plugins");
		
		// TODO set up security manager
		
		// TODO load plug-ins
		logger.fine("Load plugins ...");
		
		// TODO set up template system
		File templateDir = new File(configDir, "templates");
		logger.fine("Set up template system ...");
	}

	// --- --- singleton implementation --- ---

	private static WebCMS singleton = null;

	static synchronized void init(ServletConfig config) throws ServletException {
		if (singleton != null)
			throw new IllegalStateException("The singleton is already initialized");
		
		singleton = new WebCMS(config);
	}

	public static WebCMS getInstance() {
		if (singleton == null)
			throw new IllegalStateException("The singleton is not initialized yet");
		
		return singleton;
	}

}
