package com.ovgu.informationretrieval.wikisearcher.core.lucene;

import org.apache.lucene.analysis.core.LowerCaseFilterFactory;
import org.apache.lucene.analysis.core.StopFilterFactory;
import org.apache.lucene.analysis.custom.CustomAnalyzer;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.analysis.en.PorterStemFilterFactory;
import org.apache.lucene.analysis.standard.StandardTokenizerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Paths;

@Component
public class MyAnalyzer {

	public EnglishAnalyzer analyzer;

	public MyAnalyzer() throws IOException {
		analyzer = new EnglishAnalyzer();
//		analyzer = CustomAnalyzer.builder(Paths.get(Constants.indexDir))
//				.withTokenizer(StandardTokenizerFactory.class)
//				.addTokenFilter(LowerCaseFilterFactory.class)
//				.addTokenFilter(StopFilterFactory.class)
//				.addTokenFilter(PorterStemFilterFactory.class)
//				.build();
	}

	public EnglishAnalyzer getAnalyzer() {
		return analyzer;
	}
}
