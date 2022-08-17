package com.makina.industrialisation.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class IncidentType {

	public static final String SCREEN = "interface";
	public static final String WORD = "orthographe";
	public static final String EVENT = "evenement";
	
	public static final String NONE_SEARCH = "aucun";
	
	private IncidentType() {
	    throw new IllegalStateException("Utility class");
	}
	
	public static List<String> get(){
		return Arrays.asList(SCREEN, WORD, EVENT);	
	}

	public static List<String> getSearch() {
		List<String> searchTypes = new ArrayList<>(); 
		searchTypes.add(IncidentType.NONE_SEARCH);
		searchTypes.addAll(IncidentType.get());
		return searchTypes;
	}

}
