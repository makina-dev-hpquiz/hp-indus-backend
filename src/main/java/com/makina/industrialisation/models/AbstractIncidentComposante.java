package com.makina.industrialisation.models;

import java.util.Arrays;
import java.util.List;

public abstract class AbstractIncidentComposante {
	
	abstract List<String> get();
	
	List<String> get(String... arg){
		return Arrays.asList(arg);		
	}
}
