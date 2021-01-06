package com.ovgu.informationretrieval.wikisearcher;

import com.ovgu.informationretrieval.wikisearcher.core.api.apis.DocumentService;
import com.ovgu.informationretrieval.wikisearcher.core.model.WikiDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class ApplicationInitializer {

    @Autowired
    DocumentService service;

    @Bean("docCollection")
    public List<WikiDocument> initApplication() {
		return service.getWikiDocuments();
    }
}
