package com.ovgu.informationretrieval.wikisearcher.core.lucene;

import org.apache.lucene.analysis.custom.CustomAnalyzer;
import org.apache.lucene.analysis.en.EnglishAnalyzer;

import java.io.IOException;

public class Constants {

	public static final String id			= "id";
	public static final String title 		= "title";
	public static final String summary 		= "summary";
	public static final String content		= "content";
	public static final String imageURL 	= "image";

	public static final String score		= "score";

	public static final String indexDir		= "lucene/index";

	public static final String tokenize		= "+|+";
	public static final int max_results		= 10;
	
	public static EnglishAnalyzer getAnalyzer() throws IOException {
		return new MyAnalyzer().getAnalyzer();
	}
}
