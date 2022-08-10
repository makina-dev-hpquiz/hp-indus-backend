package com.makina.industrialisation.models;

import java.util.Arrays;
import java.util.List;

public class IncidentPriority{

	public static final String HIGHT = "haute";
	public static final String NORMAL = "normal";
	public static final String LOW = "basse";
	
	public static final String NONE_SEARCH = "aucune";

	private IncidentPriority() {
	    throw new IllegalStateException("Utility class");
	}
	
	public static List<String> get(){
		return Arrays.asList(LOW, NORMAL, HIGHT);	
	}
	
}
