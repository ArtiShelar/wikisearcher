package com.ovgu.informationretrieval.wikisearcher.core.api.apis;

import com.ovgu.informationretrieval.wikisearcher.core.lucene.Constants;
import com.ovgu.informationretrieval.wikisearcher.core.model.WikiDocument;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class DocumentService implements GenericApiService {

	@Override
	public List<WikiDocument> getWikiDocuments() {
		//String filename = "static/document_collection.json";
		File resource = null;
		try {
			resource = new ClassPathResource("static/document_collection.json").getFile();
		} catch (IOException e) { e.printStackTrace(); }

		JSONParser parser = new JSONParser();
		List<WikiDocument> collection = new ArrayList<>();

		try
		{
			FileReader reader = new FileReader(resource.getPath());
			Object obj = parser.parse(reader);

			JSONArray docList = (JSONArray) obj;
			for (int i = 0; i < docList.size(); i++) {
				JSONObject record = (JSONObject) docList.get(i);
				WikiDocument doc = new WikiDocument();
				doc.setDocTitle((String) record.get(Constants.title));
				doc.setContent((String) record.get(Constants.content));
				doc.setId((long) i+1);
				doc.setSummary((String) record.get(Constants.summary));
				doc.setImageURL((String) record.get(Constants.imageURL));

				collection.add(doc);
			}

		} catch (ParseException | IOException e) {
			e.printStackTrace();
		}
		//System.out.println(collection.size());
		return collection;
	}

//	public static void main(String[] args) {
//		DocumentService service = new DocumentService();
//		List<WikiDocument> list = service.getWikiDocuments();
//	}

}
