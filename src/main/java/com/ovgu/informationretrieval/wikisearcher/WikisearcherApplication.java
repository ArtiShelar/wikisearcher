package com.ovgu.informationretrieval.wikisearcher;

import com.ovgu.informationretrieval.wikisearcher.core.lucene.Indexer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.IOException;

@SpringBootApplication
public class WikisearcherApplication {

	public static void main(String[] args) {
//		SpringApplication application = new SpringApplication(WikisearcherApplication.class);
//		initHooks(application);
//		application.run(args);
		System.out.println("ARTI 2 : application starting");
		ConfigurableApplicationContext configurableApplicationContext = SpringApplication.run(WikisearcherApplication.class);
		ApplicationInitializer appService = configurableApplicationContext.getBean(ApplicationInitializer.class);
		try {
			Indexer indexer = configurableApplicationContext.getBean(Indexer.class);
			indexer.index();
		} catch (IOException e) { e.printStackTrace(); }
	}
//
//	@Override
//	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
//		WikisearcherApplication.initHooks(builder.application());
//		return builder.sources(WikisearcherApplication.class);
//	}
//
//	static void initHooks(SpringApplication application) {
//		System.out.println("Application initialization started");
//	}
}
