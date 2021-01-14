package com.ovgu.informationretrieval.wikisearcher.core.lucene;

import java.io.IOException;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.LeafReaderContext;
import org.apache.lucene.queries.CustomScoreProvider;
import org.apache.lucene.queries.CustomScoreQuery;
import org.apache.lucene.search.Query;

@SuppressWarnings("deprecation")
public class MyScoreQuery extends CustomScoreQuery {

	public MyScoreQuery(Query subQuery) {
		super(subQuery);
	}

	public class ScoreQueryScoreProvider extends CustomScoreProvider {

		String _field;

		public ScoreQueryScoreProvider(String field, LeafReaderContext context) {
			super(context);
			_field = field;
		}
	
		//Re-scores by adding the relevance 
		public float customScore(int doc, float subQueryScore, float[] valSrcScores) throws IOException {
			Document r = context.reader().document(doc);
			float value = Float.parseFloat(r.get(Constants.relevancy));
			//System.out.println(subQueryScore);
			return (float)(subQueryScore+value);
		}
	}
	
	protected CustomScoreProvider getCustomScoreProvider(LeafReaderContext context) throws IOException {
		//System.out.println(context);
		return new ScoreQueryScoreProvider(Constants.relevancy, context);
	}
}