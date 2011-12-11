package org.kai.cmv.lab3.data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.kai.cmv.lab3.main.GUIThread;

public class JMFdataProvider {
	private GeneralList _gl;
	private Properties _properties;

	public JMFdataProvider() {
		_gl = new GeneralList();
		_properties = new Properties();
	}

	public String linkingFile() {
		String opened = null;
		File f;

		FileDialog fd = new FileDialog(GUIThread.getShell(), SWT.OPEN);
		fd.setText("Open");
		fd.setFilterPath("/src/main/resources/");
		String[] filterExt = { "*.mpg", "*.mpeg", "*.mpe", "*.avi", "*.*" };
		fd.setFilterExtensions(filterExt);
		opened = fd.open();
		if (opened != null) {
			f = new File(opened);
		} else {
			return null;
		}
		GeneralListItem newItem = null;
		try {
			newItem = new GeneralListItem(f.getName(), f.toURI().toURL());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		if (newItem != null && !_gl.contains(newItem)) {
			_gl.addItem(newItem);
			addProperty(f);
		}
		return (f.getName());
	}

	public void unlinkingFile(GeneralListItem removingItem) {
		_gl.delItem(removingItem);
		delProperty(removingItem.getName());
	}

	public void getProperties(String path) {
		File propFile;
		_properties.clear();
		propFile = new File(getFullPath(path));
		if (!propFile.exists() || propFile.isDirectory()) {
			throw new IllegalArgumentException("File is not exist");
		}
		FileInputStream fis;
		try {
			fis = new FileInputStream(propFile);
			_properties.load(fis);
			fis.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void fillGenList() {
		_gl.clear();
		getProperties("filesList");
		for (String property : _properties.stringPropertyNames().toArray(
				new String[0])) {
			try {
				_gl.addItem(new GeneralListItem(property, new URL(_properties
						.getProperty(property))));
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		}
	}

	public void addProperty(File file) {
		try {
			_properties.put(file.getName(), file.toURI().toURL().toString());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

	public void delProperty(String key) {
		_properties.remove(key);
	}

	public void rewriteProperties(String path) {
		File propFile;
		propFile = new File(getFullPath(path));
		if (!propFile.exists() || propFile.isDirectory()) {
			throw new IllegalArgumentException("File is not exist");
		}
		FileOutputStream fos;
		try {
			fos = new FileOutputStream(propFile, false);
			_properties.store(fos, null);
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public GeneralList getData() {
		return _gl;
	}

	public String getFullPath(String path) {
		String s = File.separator;
		return "src" + s + "main" + s + "resources" + s + path;
	}

}
