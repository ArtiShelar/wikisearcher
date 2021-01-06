package com.ovgu.informationretrieval.wikisearcher.core.api;

import java.util.List;

import com.ovgu.informationretrieval.wikisearcher.core.model.WikiDocument;

public interface ApiService {

	public List<WikiDocument> getWikiDocuments();
	
}
