package com.makina.industrialisation.models;

import java.util.List;

public class IncidentPriority extends AbstractIncidentComposante {

	public static final String HIGHT = "haute";
	public static final String NORMAL = "normal";
	public static final String LOW = "basse";
	
	public static final String NONE_SEARCH = "aucune";

	@Override
	List<String> get() {
		return super.get(LOW, NORMAL, HIGHT);
	}
}
