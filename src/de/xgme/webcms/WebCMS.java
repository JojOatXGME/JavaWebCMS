package de.xgme.webcms;

import java.io.File;
import java.util.logging.Logger;

import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public final class WebCMS {
	private static WebCMS singleton = null;

	static synchronized void init(ServletConfig config) {
		if (singleton != null)
			throw new IllegalStateException("The singleton is already initialized");
		
		singleton = new WebCMS(config);
	}

	public static WebCMS getInstance() {
		if (singleton == null)
			throw new IllegalStateException("The singleton is not initialized yet");
		
		return singleton;
	}

	// --- --- object member --- ---

	private final ServletConfig servletConfig;
	private final File configDir;
	private final Logger logger;

	private WebCMS(ServletConfig servletConfig) {
		this.servletConfig = servletConfig;
		this.logger = Logger.getLogger(getClass().getSimpleName());
		
		configDir = new File(
				servletConfig.getServletContext().getRealPath("/config") // TODO deny access to this directory
				);
		
		// TODO set up basic configuration
		
		// TODO set up logger (maybe more then one process? -> more then one file)
		
		// TODO set up other thinks like connection to database
		
		// TODO set up session configuration
		
		File pluginDir = new File(configDir, "plugins");
		// TODO set up plug-in manager
		
		// TODO set up security manager
		
		// TODO load plug-ins
		
		File templateDir = new File(configDir, "templates");
		// TODO set up template system
	}

	void doGet(HttpServletRequest request, HttpServletResponse response) {
		// TODO handle get request
	}

	void doPost(HttpServletRequest request, HttpServletResponse response) {
		// TODO handle post request
	}

}
