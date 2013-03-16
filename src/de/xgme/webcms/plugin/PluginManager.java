package de.xgme.webcms.plugin;

import java.io.File;

/**
 * Manage plug-ins.
 */
public interface PluginManager {

	public void registerLoader(PluginLoader loader);

	public Plugin getPlugin(String name);

	public Plugin[] getPlugins();

	public boolean isPluginEnabled(String name);

	public boolean isPluginEnabled(Plugin plugin);

	/**
	 * Loads a plug-in from file.
	 * This method returns the loaded plug-in or null, if the file contains no plug-in.
	 * @param file File to load
	 * @return Plug-in or null, if the file contains no plug-in
	 * @throws InvalidPluginException if the plug-in file is not valid
	 * @throws UnknownDependencyException if an dependency of the plug-in could not be found
	 */
	public Plugin loadPlugin(File file)
			throws InvalidPluginException, UnknownDependencyException;

	public Plugin[] loadPlugins(File directory);

	public void disablePlugins();

	public void clearPlugins();

	public void enablePlugin(Plugin plugin);

	public void disablePlugin(Plugin plugin);

}
