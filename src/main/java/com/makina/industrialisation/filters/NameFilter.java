package com.makina.industrialisation.filters;

public class NameFilter {

	
	private NameFilter() {
	    throw new IllegalStateException("NameFilter Utility class");
	}
	
	/**
	 * Indique si la première occurence fourni possède la seconde occurence.
	 * @param String name
	 * @param String partialName
	 * @return boolean
	 */
	public static boolean hasPartialName(String name, String partialName) {
		return name.contains(partialName);
	}
}
