package com.makina.industrialisation.filters;

public class NameFilter {

	
	private NameFilter() {
	    throw new IllegalStateException("NameFilter Utility class");
	}

	
	/**
	 * Indique si la chaîne correspondant transmis possède l'indication latest 
	 * @param String name
	 * @return String
	 */
	public static boolean isLatestVersion(String name) {
		return name.contains("latest");
	}
}
