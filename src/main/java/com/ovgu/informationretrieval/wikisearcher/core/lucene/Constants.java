package com.ovgu.informationretrieval.wikisearcher.core.lucene;

import org.apache.lucene.analysis.custom.CustomAnalyzer;
import org.apache.lucene.analysis.en.EnglishAnalyzer;

import java.io.IOException;

public class Constants {

	public static final String id				= "id";
	public static final String title 			= "title";
	public static final String jsontitle 		= "docTitle";
	public static final String summary 			= "summary";
	public static final String content			= "content";
	public static final String imageURL 		= "image";
	public static final String jsonImage		= "imageURL";
	public static final String relevancy		= "relevancy";
	public static final String default_score	= "0";
	public static final String docScore			= "score";
	public static final String maxScore			= "maxScore";
	public static final String fragments		= "fragments";

	public static final String indexDir			= "lucene/index";

	public static final String doc				= "doc";
	public static final int max_results			= 10;
	
	public static EnglishAnalyzer getAnalyzer() throws IOException {
		return new MyAnalyzer().getAnalyzer();
	}
}
