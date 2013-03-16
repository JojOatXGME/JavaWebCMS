package de.xgme.webcms.plugin;

import java.io.File;
import java.io.FileFilter;

/**
 * Represents a plug-in loader, which handles the access to plug-ins.
 */
public interface PluginLoader {

	/**
	 * Loads a plug-in from the specified file.
	 * @param file File to attempt to load
	 * @return Plug-in that was contained in the specified file
	 * 
	 * @throws InvalidPluginException if the specified file is not a plug-in
	 * @throws UnknownDependencyException If a required dependency could not be found
	 */
	public Plugin loadPlugin(File file)
			throws InvalidPluginException, UnknownDependencyException;

	/**
	 * Loads a PluginDescription from the specified plug-in file
	 * @param file File to attempt to load from
	 * @return The PluginDescription from the given plug-in file
	 * @throws InvalidDescriptionException if the description is not valid
	 */
	public PluginDescription getPluginDescription(File file)
			throws InvalidDescriptionException;

	/**
	 * Gets a list of all filename filters which accept files for this plug-in loader.
	 * @return The list of filename filter for plug-ins
	 */
	public FileFilter getPluginFileFilter();

	/**
	 * Enables the specified plug-in.
	 * <br>
	 * Attempting to enable a plug-in that is already enabled will have no effect.
	 * @param plugin Plug-in to enable
	 */
	public void enablePlugin(Plugin plugin);

	/**
	 * Disable the specified plug-in.
	 * <br>
	 * Attempting to disable a plug-in that is already disabled will have no effect.
	 * @param plugin Plug-in to disable
	 */
	public void disablePlugin(Plugin plugin);

}
