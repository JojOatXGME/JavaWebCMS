package de.xgme.webcms.plugin.java;

import java.io.File;
import java.util.logging.Logger;

import de.xgme.webcms.WebCMS;
import de.xgme.webcms.plugin.AbstractPlugin;
import de.xgme.webcms.plugin.PluginDescription;
import de.xgme.webcms.plugin.PluginLogger;

public class JavaPlugin extends AbstractPlugin {

	private boolean initialized = false;
	private boolean isEnabled = false;
	private File file = null;
	private PluginClassLoader classLoader = null;
	private WebCMS cms = null;
	private PluginDescription description = null;
	private PluginLogger logger = null;
	private File dataFolder = null;

	public JavaPlugin() { }

	protected void onLoad() { }

	protected void onDisable() { }

	protected void onEnable() { }

	// ### Overrides ###

	/**
	 * Gets the folder that the plug-in data's files are located in.
	 * The folder may not yet exist.
	 * 
	 * @return Folder to store data
	 */
	public final File getDataFolder() {
		return dataFolder;
	}

	/**
	 * Gets the file which contains this plug-in.
	 * 
	 * @return File containing this plug-in
	 */
	public final File getFile() {
		return file;
	}

	@Override
	public final boolean isEnabled() {
		return isEnabled;
	}

	@Override
	public final PluginDescription getDescription() {
		return description;
	}

	@Override
	public Logger getLogger() {
		return logger;
	}

	@Override
	public final WebCMS getCms() {
		return cms;
	}

	/**
	 * Gets the class loader for this plug-in file.
	 * 
	 * @return ClassLoader for this plug-in
	 */
	protected final PluginClassLoader getClassLoader() {
		return classLoader;
	}

	// ### Non-public methods ###

	/**
	 * Initializes this plug-in with the given variables.
	 * <p />
	 * <b>This method should never be called manually.</b>
	 *
	 * @param context Context object of this plug-in
	 * @param description PluginDescription containing metadata of this plug-in
	 * @param dataFolder Folder containing the plugin's data
	 * @param file File containing this plug-in
	 * @param classLoader ClassLoader which holds this plug-in
	 */
	final void initialize(WebCMS cms, PluginDescription description,
			File dataFolder, File file, PluginClassLoader classLoader) {
		
		if (initialized)
			throw new IllegalStateException(this+" was already initialized");
		
		this.initialized = true;
		this.cms = cms;
		this.file = file;
		this.classLoader = classLoader;
		this.description = description;
		this.dataFolder = dataFolder;
		this.logger = new PluginLogger(this);
	}

	/**
	 * Sets the enabled state of this plug-in.
	 * <p />
	 * <b>The method should never be called manually.</b>
	 *
	 * @param enabled true if you wand to set enabled, otherwise false
	 */
	final void setEnabled(final boolean enabled) {
		if (this.isEnabled != enabled) {
			this.isEnabled = enabled;
			if (enabled) {
				onEnable();
			} else {
				onDisable();
			}
		}
	}

}
