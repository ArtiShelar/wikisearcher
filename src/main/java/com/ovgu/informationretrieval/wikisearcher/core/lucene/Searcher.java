package com.ovgu.informationretrieval.wikisearcher.core.lucene;

import com.ovgu.informationretrieval.wikisearcher.core.model.WikiDocument;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.highlight.*;
import org.apache.lucene.search.similarities.BM25Similarity;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
public class Searcher {
	public static List<WikiDocument> search(String q) throws IOException {
		List<WikiDocument> docs = new ArrayList<>();

		String[] searchfields = {Constants.title, Constants.content};

		IndexSearcher isearcher;

		EnglishAnalyzer analyzer = Constants.getAnalyzer();
		Directory directory;
		try {
			directory = FSDirectory.open(Paths.get(Constants.indexDir));
			IndexReader ireader = DirectoryReader.open(directory);
			isearcher = new IndexSearcher(ireader);
			isearcher.setSimilarity(new BM25Similarity());

			QueryParser parser = new MultiFieldQueryParser(searchfields, analyzer);
			Query query = parser.parse(q);

			MyScoreQuery scoreQuery = new MyScoreQuery(query);
			TopDocs topdocs = isearcher.search(scoreQuery, Constants.max_results);

			docs = processResults(topdocs, isearcher, scoreQuery, ireader, analyzer);

			ireader.close();
			directory.close();
		} catch (IOException | ParseException | InvalidTokenOffsetsException e) {
			e.printStackTrace();
		}
		return docs;
	}

	@SuppressWarnings("deprecation")
	private static List<WikiDocument> processResults(TopDocs hits, IndexSearcher isearcher, Query query, IndexReader reader, EnglishAnalyzer analyzer) throws IOException, InvalidTokenOffsetsException {
		List<WikiDocument> docs = new ArrayList<>();

		Formatter formatter = new SimpleHTMLFormatter();
		QueryScorer queryScorer = new QueryScorer(query);

		Highlighter highlighter = new Highlighter(formatter, queryScorer);

		Fragmenter fragmenter = new SimpleSpanFragmenter(queryScorer, 500);
		highlighter.setTextFragmenter(fragmenter);

		for (ScoreDoc scoreDoc : hits.scoreDocs) {
			Document hitDoc = isearcher.doc(scoreDoc.doc);

			String id = hitDoc.get(Constants.id);
			String title = hitDoc.get(Constants.title);
			String content = hitDoc.get(Constants.content);
			String summary = hitDoc.get(Constants.summary);
			String imageURL = hitDoc.get(Constants.imageURL);
			String relevancy = hitDoc.get(Constants.relevancy);

			TokenStream tokenStream = TokenSources.getAnyTokenStream(reader, scoreDoc.doc, Constants.content, analyzer);
			String[] fragments = highlighter.getBestFragments(tokenStream, content, 10000);

			WikiDocument document = new WikiDocument(Long.parseLong(id), content, title, imageURL, summary, relevancy);
			document.setScore(scoreDoc.score);
			document.setFragments(fragments);

			docs.add(document);
		}
		return docs;
	}

}
