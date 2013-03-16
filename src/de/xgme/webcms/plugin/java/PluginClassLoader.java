package de.xgme.webcms.plugin.java;

import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.Map;

public class PluginClassLoader extends URLClassLoader {
	private final JavaPluginLoader loader;
	private final Map<String, Class<?>> classes = new HashMap<String,Class<?>>();

	public PluginClassLoader(final JavaPluginLoader loader, final URL[] urls, final ClassLoader parent) {
		super(urls, parent);
		if (loader == null) throw new NullPointerException("Loader cannot be null");
		
		this.loader = loader;
	}

	@Override
	public void addURL(final URL url) {
		// override for public access level
		super.addURL(url);
	}

	@Override
	protected Class<?> findClass(final String name) throws ClassNotFoundException {
		return findClass(name, true);
	}

	Class<?> findClass(final String name, final boolean checkGlobal) throws ClassNotFoundException {
		if (isClassIllegal(name)) {
			throw new ClassNotFoundException(name);
		}
		
		Class<?> result = classes.get(name);
		
		if (result == null) {
			// TODO classes of other plug-ins are checked first? O.o
			if (checkGlobal) {
				result = loader.findClass(name);
			}
			
			if (result == null) {
				result = super.findClass(name);
			}
			
			classes.put(name, result);
		}
		
		return result;
	}

	private boolean isClassIllegal(final String name) {
		if (name.startsWith("de.xgme.webcms.")) return true;
		else                                    return false;
	}

}
