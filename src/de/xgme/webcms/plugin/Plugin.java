package de.xgme.webcms.plugin;

import java.util.logging.Logger;

import de.xgme.webcms.WebCMS;

public interface Plugin {

	/**
	 * Gets the name of the plug-in.
	 * This should return the bare name of the plug-in and should be used for comparison.
	 * @return name of the plug-in
	 */
	public String getName();

	/**
	 * Gets a value indicating whether or not this plug-in is currently enabled.
	 * @return true if this plug-in is enabled, otherwise false
	 */
	public boolean isEnabled();

	/**
	 * Gets the description object containing the details for this plug-in.
	 * @return Description object for this plug-in
	 */
	public PluginDescription getDescription();

	/**
	 * Gets the logger for this plug-in.
	 * This logger normally adds an prefix to every message to show origin of the message.
	 * @return Logger for this plug-in
	 */
	public Logger getLogger();

	/**
	 * Gets the CMS.
	 * @return The CMS
	 */
	public WebCMS getCms();

}
