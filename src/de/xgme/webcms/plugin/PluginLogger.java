package de.xgme.webcms.plugin;

import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

/**
 * This is a special {@link Logger} for plug-in which adds
 * a prefix with the name of the plug-in to any log.
 * The API for PluginLogger is exactly the same as {@link Logger}.
 */
public class PluginLogger extends Logger {
	private final String prefix;

	/**
	 * Creates a new logger for the specified plug-in.
	 * @param plugin The plug-in for which the logger is
	 */
	public PluginLogger(Plugin plugin) {
		super(plugin.getClass().getCanonicalName(), null);
		prefix = "[" + plugin.getDescription().getName() + "] ";
		this.setParent(plugin.getCms().getLogger());
		this.setLevel(Level.ALL);
	}

	@Override
	public void log(LogRecord record) {
		record.setMessage(prefix + record.getMessage());
		super.log(record);
	}

}
