package de.xgme.webcms.plugin.java;

import java.io.InputStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.SafeConstructor;

import de.xgme.webcms.plugin.InvalidDescriptionException;
import de.xgme.webcms.plugin.PluginDescription;

public class JavaPluginDescription implements PluginDescription {
	private static final Yaml yaml = new Yaml(new SafeConstructor());

	private final String name;
	private final String version;
	private final String description;
	private final String main;
	private final String website;
	private final List<String> authors;
	private final List<String> depend;
	private final List<String> softDepend;

	public JavaPluginDescription(InputStream stream) throws InvalidDescriptionException {
		this((Map<?, ?>) yaml.load(stream));
	}

	public JavaPluginDescription(Reader reader) throws InvalidDescriptionException {
		this((Map<?, ?>) yaml.load(reader));
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public List<String> getAuthors() {
		return authors;
	}

	@Override
	public String getVersion() {
		return version;
	}

	@Override
	public String getWebsite() {
		return website;
	}

	@Override
	public String getFullName() {
		return name + " v" + version;
	}

	@Override
	public List<String> getDepend() {
		return depend;
	}

	@Override
	public List<String> getSoftDepend() {
		return softDepend;
	}

	/**
	 * Gets the main class for this java plug-in.
	 * 
	 * @return classpath of main class of this java plug-in
	 */
	public String getMain() {
		return main;
	}

	private JavaPluginDescription(Map<?, ?> map) throws InvalidDescriptionException {
		try {
			name = map.get("name").toString();
			
			if (!name.matches("^[A-Za-z0-9 _.-]+$"))
				throw new InvalidDescriptionException("name '" + name + "' contains invalid characters.");
			
		} catch (NullPointerException e) {
			throw new InvalidDescriptionException("name is not defined", e);
		} catch (ClassCastException e) {
			throw new InvalidDescriptionException("name is of wrong type", e);
		}
		
		try {
			version = map.get("version").toString();
		} catch (NullPointerException e) {
			throw new InvalidDescriptionException("version is not defined", e);
		} catch (ClassCastException e) {
			throw new InvalidDescriptionException("version is of wrong type", e);
		}
		
		try {
			main = map.get("main").toString();
		} catch (NullPointerException e) {
			throw new InvalidDescriptionException("main is not defined", e);
		} catch (ClassCastException e) {
			throw new InvalidDescriptionException("main is of wrong type", e);
		}
		
		Object tmp;
		
		tmp = map.get("description");
		if (tmp != null) description = tmp.toString();
		else             description = null;
		
		tmp = map.get("website");
		if (tmp != null) website = tmp.toString();
		else             website = null;
		
		tmp = map.get("authors");
		if (tmp != null) {
			authors = new ArrayList<String>(); // TODO use ImmutableList
			if (map.get("author") != null) {
				authors.add(map.get("author").toString());
			}
			try {
				for (Object o : (Iterable<?>) tmp) {
					authors.add(o.toString());
				}
			} catch (NullPointerException e) {
				throw new InvalidDescriptionException("authors are improperly defined", e);
			} catch (ClassCastException e) {
				throw new InvalidDescriptionException("authors is of wrong type", e);
			}
		} else if (map.get("author") != null) {
			authors = new ArrayList<String>(1); // TODO use ImmutableList
			authors.add(map.get("author").toString());
		} else {
			authors = new ArrayList<String>(0); // TODO use ImmutableList
		}
		
		tmp = map.get("depend");
		if (tmp != null) {
			depend = new ArrayList<String>(); // TODO use ImmutableList
			try {
				for (Object dependency : (Iterable<?>) tmp) {
					depend.add(dependency.toString());
				}
			} catch (NullPointerException e) {
				throw new InvalidDescriptionException("depend are improperly defined", e);
			} catch (ClassCastException e) {
				throw new InvalidDescriptionException("depend is of wrong type", e);
			}
		} else {
			depend = new ArrayList<String>(0); // TODO use ImmutableList
		}
		
		tmp = map.get("softdepend");
		if (tmp != null) {
			softDepend = new ArrayList<String>(); // TODO use ImmutableList
			try {
				for (Object dependency : (Iterable<?>) tmp) {
					softDepend.add(dependency.toString());
				}
			} catch (NullPointerException e) {
				throw new InvalidDescriptionException("softdepend are improperly defined", e);
			} catch (ClassCastException e) {
				throw new InvalidDescriptionException("softdepend is of wrong type", e);
			}
		} else {
			softDepend = new ArrayList<String>(); // TODO use ImmutableList
		}
	}

}
