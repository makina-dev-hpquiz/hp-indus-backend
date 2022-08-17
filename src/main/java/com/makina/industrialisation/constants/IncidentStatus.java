package com.makina.industrialisation.constants;

import java.util.Arrays;
import java.util.List;

import com.makina.industrialisation.models.IncidentProperty;

public class IncidentStatus{

	public static final String TO_DO = "en attente";
	public static final String DOING = "en cours";
	public static final String DONE = "terminé";
		
	private IncidentStatus() {
	    throw new IllegalStateException("Utility class");
	}
	
	public static List<String> get(){
		return Arrays.asList(TO_DO, DOING, DONE);	
	}
	
	public static IncidentProperty getIncidentProperty(){
		return new IncidentProperty(get(), null, TO_DO);
	}
}
