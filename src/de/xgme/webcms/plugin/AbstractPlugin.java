package de.xgme.webcms.plugin;

public abstract class AbstractPlugin implements Plugin {

	@Override
	public final String getName() {
		return getDescription().getName();
	}

	@Override
	public final boolean equals(Object obj) {
		if (obj == null) return false;
		if (this == obj) return true;
		
		if (!(obj instanceof Plugin)) return false;
		
		return getName().equals( ((Plugin) obj).getName() ); // TODO is that enough?
	}

	@Override
	public final int hashCode() {
		return getName().hashCode(); // TODO is that enough?
	}

	@Override
	public String toString() {
		return getDescription().getFullName();
	}

}
