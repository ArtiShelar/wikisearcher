package com.ovgu.informationretrieval.wikisearcher;

import com.ovgu.informationretrieval.wikisearcher.core.lucene.Indexer;
import org.springframework.boot.ApplicationContextFactory;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.IOException;

public class ServletInitializer extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		System.out.println("ARTI : starting application");
		application.sources(WikisearcherApplication.class);
		//application.contextFactory(ApplicationContextFactory.DEFAULT);
		ConfigurableApplicationContext configurableApplicationContext = application.run();
		System.out.println("ConfigurableApplicationContext :: "+configurableApplicationContext);
		ApplicationInitializer appService = configurableApplicationContext.getBean(ApplicationInitializer.class);
		try {
			Indexer indexer = configurableApplicationContext.getBean(Indexer.class);
			indexer.index();
		} catch (IOException e) { e.printStackTrace(); }
		System.out.println("ARTI : application started");
		return application.sources(WikisearcherApplication.class);
	}

}
