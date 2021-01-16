package com.ovgu.informationretrieval.wikisearcher.core.lucene;

import com.ovgu.informationretrieval.wikisearcher.core.model.WikiDocument;
import org.apache.commons.io.FileUtils;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.similarities.BM25Similarity;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

@Service
public class Indexer {

	@Autowired
	List<WikiDocument> documentList;

	public void index() throws IOException {
		EnglishAnalyzer analyzer = Constants.getAnalyzer();

		File folder = new File(Constants.indexDir);
		if (folder.exists()) {
			FileUtils.cleanDirectory(folder);
		}
		System.out.println("Indexing " + documentList.size() + " documents");
		Directory directory;
		try {
			directory = FSDirectory.open(Paths.get(Constants.indexDir));

			IndexWriterConfig config = new IndexWriterConfig(analyzer);
			config.setOpenMode(IndexWriterConfig.OpenMode.CREATE_OR_APPEND);
			config.setSimilarity(new BM25Similarity());

			IndexWriter iwriter = new IndexWriter(directory, config);

			for (WikiDocument doc : documentList) {
				iwriter.addDocument(addWikiDocument(doc));
			}
			iwriter.close();
			System.out.println("Indexing complete");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static Document createDoc(WikiDocument wiki) {
		Document doc = new Document();

		doc.add(new TextField(Constants.id, String.valueOf(wiki.getId()), Field.Store.YES));
		doc.add(new TextField(Constants.title, wiki.getTitle(), Field.Store.YES));
		doc.add(new TextField(Constants.summary, wiki.getSummary(), Field.Store.YES));
		doc.add(new TextField(Constants.imageURL, wiki.getImage(), Field.Store.YES));
		doc.add(new TextField(Constants.content, wiki.getContent(), Field.Store.YES));

		return doc;
	}

	private static Document addWikiDocument(WikiDocument wiki) {
		Document doc = createDoc(wiki);

		doc.add(new TextField(Constants.relevancy, Constants.default_score, Field.Store.YES));
		return doc;
	}

	public void updateRelevanceScoreInIndex(WikiDocument wiki, String maxScore) throws IOException {
		Document doc = createDoc(wiki);

		float relevancy = Float.parseFloat(wiki.getRelevancy()) + Float.parseFloat(maxScore);

		doc.add(new TextField(Constants.relevancy, String.valueOf(relevancy), Field.Store.YES));

		EnglishAnalyzer analyzer = Constants.getAnalyzer();
		Directory directory;

		try {
			directory = FSDirectory.open(Paths.get(Constants.indexDir));

			IndexWriterConfig config = new IndexWriterConfig(analyzer);
			config.setOpenMode(IndexWriterConfig.OpenMode.CREATE_OR_APPEND);
			config.setSimilarity(new BM25Similarity());

			IndexWriter iwriter = new IndexWriter(directory, config);

			iwriter.updateDocument(new Term(Constants.id, String.valueOf(wiki.getId())), doc);

			System.out.println("Wiki ID updated : "+wiki.getId());
			iwriter.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

}
