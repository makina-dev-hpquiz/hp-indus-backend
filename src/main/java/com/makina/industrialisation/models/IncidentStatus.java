package com.makina.industrialisation.models;

import java.util.List;

public class IncidentStatus extends AbstractIncidentComposante{

	public static final String SCREEN = "interface";
	public static final String WORD = "ortographe";
	public static final String EVENT = "evenement";
	
	public static final String NONE_SEARCH = "aucune";
	
	@Override
	List<String> get() {
		return super.get(SCREEN, WORD, EVENT);
	}
}
