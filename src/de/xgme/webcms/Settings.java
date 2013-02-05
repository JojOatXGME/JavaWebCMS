package de.xgme.webcms;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.util.Map;

import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.SafeConstructor;

class Settings {

	private String sqlDriver = null;
	private String sqlUrl    = null;
	private String sqlUser   = "";
	private String sqlPasswd = "";
	private boolean sqlSingleConnection = false;

	private String loggerName     = "JavaWebCMS";
	private String logfile        = "system.log";
	private String logfileWarning = "none";
	private String logfileSevere  = "none";

	private String sessionName      = "default";
	private String sessionDomain    = "default";
	private String sessionPath      = "default";
	private Integer sessionMaxAge   = null;
	private boolean sessionHttpOnly = false; // TODO include an option "auto"?
	private boolean sessionSecure   = false;

	String getSqlDriver() {
		return sqlDriver;
	}

	String getSqlUrl() {
		return sqlUrl;
	}

	String getSqlUser() {
		return sqlUser;
	}

	String getSqlPasswd() {
		return sqlPasswd;
	}

	boolean getSqlSingleConnection() {
		return sqlSingleConnection;
	}

	String getLoggerName() {
		return loggerName;
	}

	String getLogfile() {
		return logfile;
	}

	String getLogfileWarning() {
		return logfileWarning;
	}

	String getLogfileSevere() {
		return logfileSevere;
	}

	String getSessionName() {
		return sessionName;
	}

	String getSessionDomain() {
		return sessionDomain;
	}

	String getSessionPath() {
		return sessionPath;
	}

	Integer getSessionMaxAge() {
		return sessionMaxAge;
	}

	boolean isSessionHttpOnly() {
		return sessionHttpOnly;
	}

	boolean isSessionSecure() {
		return sessionSecure;
	}

	void setSqlDriver(String sqlDriver) {
		this.sqlDriver = sqlDriver;
	}

	void setSqlUrl(String sqlUrl) {
		this.sqlUrl = sqlUrl;
	}

	void setSqlUser(String sqlUser) {
		this.sqlUser = sqlUser;
	}

	void setSqlPasswd(String sqlPasswd) {
		this.sqlPasswd = sqlPasswd;
	}

	void setSqlSingleConnection(boolean sqlSingleConnection) {
		this.sqlSingleConnection = sqlSingleConnection;
	}

	void setLoggerName(String loggerName) {
		this.loggerName = loggerName;
	}

	void setLogfile(String logfile) {
		this.logfile = logfile;
	}

	void setLogfileWarning(String logfileWarning) {
		this.logfileWarning = logfileWarning;
	}

	void setLogfileSevere(String logfileSevere) {
		this.logfileSevere = logfileSevere;
	}

	void setSessionName(String sessionName) {
		this.sessionName = sessionName;
	}

	void setSessionDomain(String sessionDomain) {
		this.sessionDomain = sessionDomain;
	}

	void setSessionPath(String sessionPath) {
		this.sessionPath = sessionPath;
	}

	void setSessionMaxAge(Integer sessionMaxAge) {
		this.sessionMaxAge = sessionMaxAge;
	}

	void setSessionHttpOnly(boolean sessionHttpOnly) {
		this.sessionHttpOnly = sessionHttpOnly;
	}

	void setSessionSecure(boolean sessionSecure) {
		this.sessionSecure = sessionSecure;
	}

	Settings(Reader reader) throws InvalidConfigurationException {
		Yaml yaml = new Yaml(new SafeConstructor());
		Map<?,?> map = (Map<?, ?>) yaml.load(reader);
		
		Map<?,?> sqlMap = readMap(map, "database", true);
		if (sqlMap != null) {
			sqlDriver = readString(sqlMap, "driver", sqlDriver);
			sqlUrl    = readString(sqlMap, "url",    sqlUrl);
			sqlUser   = readString(sqlMap, "user",   sqlUser);
			sqlPasswd = readString(sqlMap, "passwd", sqlPasswd);
			// TODO load sqlSingleConnection
		}
		
		Map<?,?> logMap = readMap(map, "logging", true);
		if (logMap != null) {
			loggerName     = readString(logMap, "logger_name",     loggerName);
			logfile        = readString(logMap, "logfile",         logfile);
			try {
				new File(logfile).getCanonicalFile();
			} catch (IOException e) {
				throw new InvalidConfigurationException(e);
			}
			logfileWarning = readString(logMap, "logfile_warning", logfileWarning);
			try {
				new File(logfileWarning).getCanonicalFile();
			} catch (IOException e) {
				throw new InvalidConfigurationException(e);
			}
			logfileSevere  = readString(logMap, "logfile_severe",  logfileSevere);
			try {
				new File(logfileSevere).getCanonicalFile();
			} catch (IOException e) {
				throw new InvalidConfigurationException(e);
			}
		}
		
		Map<?,?> sessionMap = readMap(map, "session", true);
		if (map != null) {
			sessionName   = readString(sessionMap, "name",   sessionName);
			sessionDomain = readString(sessionMap, "domain", sessionDomain);
			sessionPath   = readString(sessionMap, "path",   sessionPath);
			// TODO Integer sessionMaxAge
			// TODO boolean sessionHttpOnly
			// TODO boolean sessionSecure
		}
		
		//if (!name.matches("^[A-Za-z0-9 _.-]+$"))
		//	throw new InvalidConfigurationException("name '" + name + "' contains invalid characters.");
	}

	private <T> T readObject(Class<T> type, Map<?,?> map, String field, boolean optional) throws InvalidConfigurationException {
		try {
			return type.cast(map.get(field));
		} catch (NullPointerException e) {
			if (optional) return null;
			else          throw new InvalidConfigurationException(field+" is not defined", e);
		} catch (ClassCastException e) {
			throw new InvalidConfigurationException(field+" is of wrong type", e);
		}
	}

	private Map<?,?> readMap(Map<?,?> map, String field, boolean optional) throws InvalidConfigurationException {
		try {
			return (Map<?,?>) map.get(field);
		} catch (NullPointerException e) {
			if (optional) return null;
			else          throw new InvalidConfigurationException(field+" is not defined", e);
		} catch (ClassCastException e) {
			throw new InvalidConfigurationException(field+" is of wrong type", e);
		}
	}

	private String readString(Map<?,?> map, String field, boolean optional) throws InvalidConfigurationException {
		try {
			return map.get(field).toString();
		} catch (NullPointerException e) {
			if (optional) return null;
			else          throw new InvalidConfigurationException(field+" is not defined", e);
		} catch (ClassCastException e) {
			throw new InvalidConfigurationException(field+" is of wrong type", e);
		}
	}

	private String readString(Map<?,?> map, String field, String def) throws InvalidConfigurationException {
		String value = readString(map, field, true);
		if (value == null) return def;
		else               return value;
	}

}
