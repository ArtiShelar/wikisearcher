package com.ovgu.informationretrieval.wikisearcher.core.rest;

import com.ovgu.informationretrieval.wikisearcher.core.api.ApiService;
import com.ovgu.informationretrieval.wikisearcher.core.lucene.Indexer;
import com.ovgu.informationretrieval.wikisearcher.core.lucene.Searcher;
import com.ovgu.informationretrieval.wikisearcher.core.model.WikiDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
public class SearchRestController {

	@Autowired
	private ApiService apiService;

	@RequestMapping("/index")
	public void index() throws IOException {
		//List<WikiDocument> docs = apiService.getWikiDocuments();
		new Indexer().index();
	}

	@RequestMapping("/search")
	public List<WikiDocument> search(@RequestParam(value = "q", required = true) String query) throws IOException {
		return Searcher.search(query);
	}

	@PostMapping(value = "/updateRelevance", consumes = {"multipart/form-data"})
	public ResponseEntity<String> updateRelevance(@RequestPart("doc") WikiDocument doc, @RequestPart("maxScore") String maxScore) throws IOException {
		new Indexer().updateRelevanceScoreInIndex(doc, maxScore);
		return ResponseEntity.ok().build();
	}

	@RequestMapping("/hello")
	public String hello() {
		return "Hello, spring boot is working!";
	}

//	@RequestMapping("/findSimilar")
//	public List<WikiDocument> findSimilar(@RequestParam(value = "id", required = true) long id) {
//		List<WikiDocument> docs = Searcher.findSimilar(id);
//		return docs;
//	}

}
