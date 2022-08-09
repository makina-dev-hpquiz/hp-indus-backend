package com.makina.industrialisation.models;

import java.util.Arrays;
import java.util.List;

public class IncidentType {

	public static final String SCREEN = "interface";
	public static final String WORD = "ortographe";
	public static final String EVENT = "evenement";
	
	public static List<String> get(){
		
		return Arrays.asList(SCREEN, WORD, EVENT);	
	}

}
