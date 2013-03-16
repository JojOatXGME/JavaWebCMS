package de.xgme.webcms.plugin.java;

import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.net.URL;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.logging.Level;

import org.yaml.snakeyaml.error.YAMLException;

import de.xgme.webcms.WebCMS;
import de.xgme.webcms.plugin.InvalidDescriptionException;
import de.xgme.webcms.plugin.InvalidPluginException;
import de.xgme.webcms.plugin.Plugin;
import de.xgme.webcms.plugin.PluginLoader;
import de.xgme.webcms.plugin.UnknownDependencyException;

public class JavaPluginLoader implements PluginLoader {
	private final WebCMS cms;
	private final List<PluginClassLoader> classLoaders = new CopyOnWriteArrayList<PluginClassLoader>();

	public JavaPluginLoader(WebCMS cms) {
		this.cms = cms;
	}

	@Override
	public Plugin loadPlugin(File file)
			throws InvalidPluginException, UnknownDependencyException {
		
		if (file == null) throw new IllegalArgumentException("File cannot be null");
		
		if (!file.isFile()) {
			throw new InvalidPluginException(new FileNotFoundException(file.getPath() + " does not exist"));
		}
		
		final JavaPluginDescription description = getPluginDescription(file);
		final File dataFolder = getDataFolder(file);
		
		try {
			URL[] urls = new URL[] {file.toURI().toURL()};
			final PluginClassLoader loader = new PluginClassLoader(this, urls, getClass().getClassLoader());
			
			Class<?> mainClass = Class.forName(description.getMain(), true, loader);
			Class<? extends JavaPlugin> pluginClass = mainClass.asSubclass(JavaPlugin.class);
			
			Constructor<? extends JavaPlugin> constructor = pluginClass.getConstructor();
			
			JavaPlugin plugin = constructor.newInstance();
			
			plugin.initialize(cms, description, dataFolder, file, loader);
			plugin.onLoad();
			
			return plugin;
		} catch (Throwable t) {
			throw new InvalidPluginException(t);
		}
	}

	@Override
	public JavaPluginDescription getPluginDescription(File file)
			throws InvalidDescriptionException {
		
		if (file == null) throw new IllegalArgumentException("File cannot be null");
		
		JarFile jar = null;
		InputStream stream = null;
		
		try {
			jar = new JarFile(file);
			JarEntry entry = jar.getJarEntry("plugin.yml");
			
			if (entry == null) {
				throw new InvalidDescriptionException(new FileNotFoundException("Plugin does not contain plugin.yml"));
			}
			
			stream = jar.getInputStream(entry);
			return new JavaPluginDescription(stream);
		} catch (IOException | YAMLException e) {
			throw new InvalidDescriptionException(e);
		} finally {
			if (jar != null) {
				try {
					jar.close();
				} catch (IOException e) { }
			}
			if (stream != null) {
				try {
					stream.close();
				} catch (IOException e) { }
			}
		}
	}

	@Override
	public void enablePlugin(Plugin plugin) {
		if (!(plugin instanceof JavaPlugin)) throw new IllegalArgumentException("Plugin is not associated with this PluginLoader");
		
		if (!plugin.isEnabled()) {
			final JavaPlugin jPlugin = (JavaPlugin) plugin;
			
			if (!classLoaders.contains(jPlugin.getClassLoader())) {
				classLoaders.add(jPlugin.getClassLoader());
			}
			
			try {
				jPlugin.setEnabled(true);
			} catch (Throwable t) {
				jPlugin.getLogger().log(
						Level.SEVERE,
						"Error occurred while enabling " + plugin.getDescription().getFullName(),
						t);
			}
		}
	}

	@Override
	public void disablePlugin(Plugin plugin) {
		if (!(plugin instanceof JavaPlugin)) throw new IllegalArgumentException("Plugin is not associated with this PluginLoader");
		
		if (plugin.isEnabled()) {
			final JavaPlugin jPlugin = (JavaPlugin) plugin;
			
			try {
				jPlugin.setEnabled(false);
			} catch (Throwable t) {
				jPlugin.getLogger().log(
						Level.SEVERE,
						"Error occurred while disabling " + plugin.getDescription().getFullName(),
						t);
			}
			
			this.classLoaders.remove(jPlugin.getClassLoader());
		}
	}

	@Override
	public FileFilter getPluginFileFilter() {
		return JarUtil.jarFilter;
	}

	/**
	 * Search in all enabled plug-ins for the specified class.
	 *
	 * @param name Name of the class
	 * @return The Class or null, if the class was not found
	 */
	Class<?> findClass(final String name) {
		for (PluginClassLoader loader : classLoaders) {
			try {
				Class<?> clazz = loader.findClass(name, false);
				if (clazz != null) {
					return clazz;
				}
			} catch (ClassNotFoundException e) { }
		}
		
		return null;
	}

	private File getDataFolder(final File pluginFile) {
		File dataFolder;
		
		final String name = pluginFile.getName();
		int index = name.lastIndexOf(".");
		if (index > 0) {
			dataFolder = new File(pluginFile.getParentFile(), name.substring(0, index));
		} else {
			dataFolder = new File(pluginFile.getParentFile(), name + "_");
		}
		
		return dataFolder;
	}

}
