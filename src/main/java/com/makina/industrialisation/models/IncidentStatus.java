package com.makina.industrialisation.models;

import java.util.Arrays;
import java.util.List;

public class IncidentStatus{

	public static final String TO_DO = "en attente";
	public static final String DOING = "en cours";
	public static final String DONE = "terminé";
	
	public static final String NONE_SEARCH = "aucune";
	
	private IncidentStatus() {
	    throw new IllegalStateException("Utility class");
	}
	
	public static List<String> get(){
		return Arrays.asList(TO_DO, DOING, DONE);	
	}
	
}
