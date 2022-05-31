package com.makina.industrialisation.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.makina.industrialisation.models.Incident;

@Service
public class BugsManager {
	
	public List<Incident> incidents;
	
	public BugsManager() {
		this.incidents = new ArrayList();
	}
}
