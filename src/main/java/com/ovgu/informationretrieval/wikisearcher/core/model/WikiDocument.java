package com.ovgu.informationretrieval.wikisearcher.core.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ovgu.informationretrieval.wikisearcher.core.lucene.Constants;
import org.apache.tomcat.util.bcel.Const;

import java.util.Arrays;

public class WikiDocument {

	@JsonProperty(Constants.id)
	private Long id;

	@JsonProperty(Constants.content)
	private String content;

	@JsonProperty(Constants.jsontitle)
	private String title;

	@JsonProperty(Constants.summary)
	private String summary;

	@JsonProperty(Constants.jsonImage)
	private String image;

	@JsonProperty(Constants.docScore)
	private Float score;

	@JsonProperty(Constants.fragments)
	private String[] fragments;

	@JsonProperty(Constants.relevancy)
	private String relevancy;

	public WikiDocument(){
	}

	public WikiDocument(Long id, String content, String docTitle, String imageURL, String summary, String relevancy) {
		this.id = id;
		this.content = content;
		this.title = docTitle;
		this.image = imageURL;
		this.summary = summary;
		this.relevancy = relevancy;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getContent() { return content; }

	public void setContent(String content) {
		this.content = content;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSummary() { return summary; }

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) { this.image = image; }

	public Float getScore() { return score; }

	public void setScore(Float score) { this.score = score; }

	public String[] getFragments() { return fragments; }

	public void setFragments(String[] fragments) { this.fragments = fragments; }

	public String getRelevancy() { return relevancy; }

	public void setRelevancy(String relevancy) { this.relevancy = relevancy; }

	@Override
	public String toString() {
		return "WikiDocument{" +
				"id=" + id +
				", content='" + content + '\'' +
				", title='" + title + '\'' +
				", summary='" + summary + '\'' +
				", image='" + image + '\'' +
				", score=" + score +
				", fragments=" + Arrays.toString(fragments) +
				", relevancy='" + relevancy + '\'' +
				'}';
	}
}
