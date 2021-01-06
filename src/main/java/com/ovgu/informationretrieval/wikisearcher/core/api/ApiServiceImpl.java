package com.ovgu.informationretrieval.wikisearcher.core.api;

import com.ovgu.informationretrieval.wikisearcher.core.api.apis.DocumentService;
import com.ovgu.informationretrieval.wikisearcher.core.model.WikiDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApiServiceImpl implements ApiService {

	@Autowired
	private DocumentService docService;

	@Override
	public List<WikiDocument> getWikiDocuments() {

		return docService.getWikiDocuments();
	}

}
