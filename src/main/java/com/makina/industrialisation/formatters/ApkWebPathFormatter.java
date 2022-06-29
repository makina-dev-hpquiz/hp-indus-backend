package com.makina.industrialisation.formatters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.makina.industrialisation.configuration.AndroidPackageManagerConfiguration;

@Service
public class ApkWebPathFormatter extends AbstractWebPathFormatter {

	@Autowired
	AndroidPackageManagerConfiguration configuration;
	
	@Override
	protected String getFolderName() {		
		return configuration.getFolderName();
	}

}
