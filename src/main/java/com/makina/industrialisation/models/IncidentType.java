package com.makina.industrialisation.models;

import java.util.Arrays;
import java.util.List;

public class IncidentType {

	public static final String SCREEN = "interface";
	public static final String WORD = "orthographe";
	public static final String EVENT = "evenement";
	
	private IncidentType() {
	    throw new IllegalStateException("Utility class");
	}
	
	public static List<String> get(){
		
		return Arrays.asList(SCREEN, WORD, EVENT);	
	}

}
