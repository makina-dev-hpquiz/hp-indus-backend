package com.makina.industrialisation;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.makina.industrialisation.configuration.AndroidPackageManagerConfiguration;
import com.makina.industrialisation.configuration.TomcatConfiguration;
import com.makina.industrialisation.controllers.ApkController;
import com.makina.industrialisation.controllers.IncidentController;
import com.makina.industrialisation.extractors.DateExtractor;
import com.makina.industrialisation.extractors.VersionExtractor;
import com.makina.industrialisation.formatters.ApkWebPathFormatter;
import com.makina.industrialisation.formatters.DateFormatter;
import com.makina.industrialisation.formatters.ImgWebPathFormatter;
import com.makina.industrialisation.formatters.SizeFormatter;
import com.makina.industrialisation.services.AndroidPackageManager;
import com.makina.industrialisation.services.IncidentService;

@SpringBootTest
class HarryPotterQuizIndusBackendApplicationTests {

	// Controllers
	@Autowired 
	private ApkController apkController;
	
	@Autowired
	private IncidentController incidentController;
	
	//Configurations
	@Autowired
	private TomcatConfiguration tomcatConfiguration;
	
	@Autowired
	private AndroidPackageManagerConfiguration apkConfiguration;
	
	//Services
	@Autowired
	private DateExtractor dateExtractor;

	@Autowired
	private VersionExtractor versionExtractor;

	@Autowired
	private ApkWebPathFormatter apkWebPathFormatter;
	
	@Autowired
	private ImgWebPathFormatter imgWebPathFormatter;

	@Autowired
	private DateFormatter dateFormatter;

	@Autowired
	private SizeFormatter sizeFormatter;

	@Autowired
	private AndroidPackageManager apkManager;

	@Autowired
	private IncidentService incidentService;
	
	
	@Test
	void contextLoads() {
		//Controllers
		assertNotNull(apkController);
		assertNotNull(incidentController);
		
		//Configurations
		assertNotNull(tomcatConfiguration);
		assertNotNull(apkConfiguration);
		
		//Services
		assertNotNull(dateExtractor);
		assertNotNull(versionExtractor);
		assertNotNull(apkWebPathFormatter);
		assertNotNull(imgWebPathFormatter);
		assertNotNull(dateFormatter);
		assertNotNull(sizeFormatter);
		assertNotNull(apkManager);
		assertNotNull(incidentService);
	}

}
