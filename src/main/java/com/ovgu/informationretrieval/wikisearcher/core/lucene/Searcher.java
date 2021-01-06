package com.ovgu.informationretrieval.wikisearcher.core.lucene;

import com.ovgu.informationretrieval.wikisearcher.core.model.WikiDocument;
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

		EnglishAnalyzer analyzer = Constants.getAnalyzer();
		Directory directory;
		try {
			directory = FSDirectory.open(Paths.get(Constants.indexDir));
			IndexReader ireader = DirectoryReader.open(directory);
			IndexSearcher isearcher = new IndexSearcher(ireader);
			isearcher.setSimilarity(new BM25Similarity());

			QueryParser parser = new MultiFieldQueryParser(searchfields, analyzer);
			Query query = parser.parse(q);

			TopDocs topdocs = isearcher.search(query, Constants.max_results);

			docs = processResults(topdocs, isearcher);

			ireader.close();
			directory.close();
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
		return docs;
	}

//	public static List<WikiDocument> findSimilar(long id) throws IOException {
//		List<WikiDocument> movies = new ArrayList<WikiDocument>();
//
//		CustomAnalyzer analyzer = Constants.getAnalyzer();
//		File folder = new File(Constants.indexDir);
//		Directory directory;
//		try {
//			directory = FSDirectory.open(Paths.get(Constants.indexDir));
//			IndexReader ireader = DirectoryReader.open(directory);
//			IndexSearcher isearcher = new IndexSearcher(ireader);
//
//			// Buscamos la película
//			Query query = NumericRangeQuery.newLongRange(Constants.id, 1, id, id, true, true);
//			TopDocs topdoc = isearcher.search(query, 1);
//			if (topdoc.scoreDocs == null || topdoc.scoreDocs.length == 0) {
//				return movies;
//			}
//			int docId = topdoc.scoreDocs[0].doc;
//			Movie movie = processResults(topdoc.scoreDocs, isearcher).get(0);
//
//			// Similares
//			MoreLikeThis mlt = new MoreLikeThis(ireader);
//			mlt.setMinTermFreq(0);
//			mlt.setMinDocFreq(0);
//			mlt.setBoost(false);
//			mlt.setFieldNames(new String[] { Constants.genres, Constants.directorsLiteral });
//			mlt.setAnalyzer(analyzer);
//
//			Query queryLike = mlt.like(docId);
//
//			BooleanQuery booleanQuery = new BooleanQuery();
//			BooleanClause clause = new BooleanClause(queryLike, Occur.SHOULD);
//			booleanQuery.add(clause);
//			booleanQuery.add(NumericRangeQuery.newIntRange(Constants.year, movie.getYear() - 2,
//					movie.getYear() + 2, true, true), Occur.SHOULD);
//
//			TopDocs topdocs = isearcher.search(booleanQuery, 10 + 1);
//
//			// Procesamos los resultados
//			movies = processResults(topdocs.scoreDocs, isearcher);
//			// Eliminamos la propia película de la lista
//			if (!movies.isEmpty() && movies.contains(movie)) {
//				movies.remove(movie);
//			}
//
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//
//		return movies;
//	}

	private static List<WikiDocument> processResults(TopDocs hits, IndexSearcher isearcher) throws IOException {
		List<WikiDocument> docs = new ArrayList<>();
		for (ScoreDoc scoreDoc : hits.scoreDocs) {
			Document hitDoc = isearcher.doc(scoreDoc.doc);

			String id = hitDoc.get(Constants.id);
			String title = hitDoc.get(Constants.title);
			String content = hitDoc.get(Constants.content);
			String summary = hitDoc.get(Constants.summary);
			String imageURL = hitDoc.get(Constants.imageURL);

			WikiDocument document = new WikiDocument(Long.parseLong(id), content, title, imageURL, summary);
			document.setScore(scoreDoc.score);

			docs.add(document);
		}
		return docs;
	}

	//private void highlight_matches

}
