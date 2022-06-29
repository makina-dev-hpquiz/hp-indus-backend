package com.makina.industrialisation.formatters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.makina.industrialisation.configuration.TomcatConfiguration;

@Service
public class ImgWebPathFormatter extends AbstractWebPathFormatter {

	@Autowired
	TomcatConfiguration configuration;
	
	@Override
	protected String getFolderName() {
		return configuration.getImgFolder();
	}

}
