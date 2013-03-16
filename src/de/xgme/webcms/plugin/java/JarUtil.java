package de.xgme.webcms.plugin.java;

import java.io.File;
import java.io.FileFilter;

import javax.swing.filechooser.FileNameExtensionFilter;

class JarUtil {

	static final String[] fileExtensions   = {"jar"};

	static final String defaultExtension = fileExtensions[0];

	static final FileNameExtensionFilter extensionFilter =
			new FileNameExtensionFilter("Java Archive", fileExtensions);

	static final FileFilter jarFilter = new FileFilter() {
		@Override
		public boolean accept(File pathname) {
			if (!pathname.isFile()) return false;
			return extensionFilter.accept(pathname);
		}
	};


	static String cutExtension(String fileName) {
		for (String ex : fileExtensions) {
			if (fileName.endsWith("."+ex)) {
				return fileName.substring(0, fileName.length() - (ex.length()+1) );
			}
		}
		return fileName;
	}

	static String addExtension(String fileName) {
		for (String ex : fileExtensions) {
			if (fileName.endsWith("."+ex)) {
				return fileName;
			}
		}
		return fileName + "." + defaultExtension;
	}

	private JarUtil() { } // make it impossible to create a instance

}
