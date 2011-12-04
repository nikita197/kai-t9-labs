package org.kai.cmv.lab3.data;

import java.net.URL;

public class GeneralListItem {
	private String name;
	private URL url;

	public GeneralListItem(String name, URL url) {
		this.name = name;
		this.url = url;
	}

	public String getName() {
		return name;
	}

	public URL getURL() {
		return url;
	}

}
