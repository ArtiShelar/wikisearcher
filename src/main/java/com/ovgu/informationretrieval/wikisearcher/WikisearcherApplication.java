package com.ovgu.informationretrieval.wikisearcher;

import com.ovgu.informationretrieval.wikisearcher.core.lucene.Indexer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.IOException;

@SpringBootApplication
public class WikisearcherApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		System.out.println("Application Started");
		//SpringApplication.run(WikisearcherApplication.class, args);
		ConfigurableApplicationContext configurableApplicationContext = SpringApplication.run(WikisearcherApplication.class);
		ApplicationInitializer appService = configurableApplicationContext.getBean(ApplicationInitializer.class);
		try {
			Indexer indexer = configurableApplicationContext.getBean(Indexer.class);
			indexer.index();
		} catch (IOException e) { e.printStackTrace(); }

	}

}
