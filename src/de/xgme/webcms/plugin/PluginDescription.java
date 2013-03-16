package de.xgme.webcms.plugin;

import java.util.List;

/**
 * Description of plug-in.
 * Instances represents a plug-in description.
 */
public interface PluginDescription {

	/**
	 * Gets the name of the plug-in.
	 * @return Name of this plug-in
	 */
	public String getName();

	/**
	 * Gets the textual description of the plug-in.
	 * @return Textual description of the plug-in
	 */
	public String getDescription();

	/**
	 * Gets the authors of the plug-in.
	 * @return Authors of the plug-in
	 */
	public List<String> getAuthors();

	/**
	 * Gets the version of the plug-in.
	 * @return Vesion of the plug-in
	 */
	public String getVersion();

	/**
	 * Gets the website of the plug-in.
	 * @return Website if the plug-in
	 */
	public String getWebsite();

	/**
	 * Gets the full name including the version of the plug-in.
	 * @return Full Name of the plug-in
	 */
	public String getFullName();

	/**
	 * Gets the list of depend plug-ins.
	 * @return List of depend plug-ins
	 */
	public List<String> getDepend();

	/**
	 * Gets the list of optional depend plug-ins.
	 * @return List of soft depend plug-ins
	 */
	public List<String> getSoftDepend();

}
