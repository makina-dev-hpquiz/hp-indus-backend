package com.makina.industrialisation.models;

import java.util.List;

public class IncidentType extends AbstractIncidentComposante{

	public static final String TO_DO = "en attente";
	public static final String DOING = "en cours";
	public static final String DONE = "terminé";
	
	@Override
	List<String> get() {
		return super.get(TO_DO, DOING, DONE);
	}
}
