package com.ovgu.informationretrieval.wikisearcher;

import com.ovgu.informationretrieval.wikisearcher.core.lucene.Indexer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.IOException;

@SpringBootApplication
public class WikisearcherApplication {

	public static void main(String[] args) {
		//SpringApplication.run(WikisearcherApplication.class, args);
		ConfigurableApplicationContext configurableApplicationContext = SpringApplication.run(WikisearcherApplication.class);
		ApplicationInitializer appService = configurableApplicationContext.getBean(ApplicationInitializer.class);
		try {
			Indexer indexer = configurableApplicationContext.getBean(Indexer.class);
			indexer.index();
		} catch (IOException e) { e.printStackTrace(); }
		System.out.println("Application Started");
	}

}
