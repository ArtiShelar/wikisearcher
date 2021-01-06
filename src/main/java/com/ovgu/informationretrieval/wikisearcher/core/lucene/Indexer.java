package com.ovgu.informationretrieval.wikisearcher.core.lucene;

import com.ovgu.informationretrieval.wikisearcher.core.model.WikiDocument;
import org.apache.lucene.analysis.custom.CustomAnalyzer;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.LongPoint;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
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
		System.out.println("Indexing " + documentList.size() + " documents");

		EnglishAnalyzer analyzer = Constants.getAnalyzer();

		File folder = new File(Constants.indexDir);
		if (folder.exists()) {
			folder.delete();
		}
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

	private static Document addWikiDocument(WikiDocument wiki) {
		Document doc = new Document();

		doc.add(new LongPoint(Constants.id, wiki.getId()));
		doc.add(new TextField(Constants.title, wiki.getDocTitle(), Field.Store.YES));
		doc.add(new TextField(Constants.summary, wiki.getSummary(), Field.Store.YES));
		doc.add(new TextField(Constants.imageURL, wiki.getImageURL(), Field.Store.YES));
		doc.add(new TextField(Constants.content, wiki.getContent(), Field.Store.YES));

		return doc;
	}

}
