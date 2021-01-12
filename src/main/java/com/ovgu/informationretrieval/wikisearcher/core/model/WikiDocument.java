package com.ovgu.informationretrieval.wikisearcher.core.model;

public class WikiDocument {

	private Long id;

	private String content;

	private String docTitle;

	private String summary;

	private String imageURL;

	private Float score;

	private String[] fragments;

	public WikiDocument(){
	}

	public WikiDocument(Long id, String content, String docTitle, String imageURL, String summary) {
		this.id = id;
		this.content = content;
		this.docTitle = docTitle;
		this.imageURL = imageURL;
		this.summary = summary;
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

	public String getDocTitle() {
		return docTitle;
	}

	public void setDocTitle(String docTitle) {
		this.docTitle = docTitle;
	}

	public String getSummary() { return summary; }

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getImageURL() {
		return imageURL;
	}

	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}

	public Float getScore() { return score; }

	public void setScore(Float score) { this.score = score; }

	public String[] getFragments() { return fragments; }

	public void setFragments(String[] fragments) { this.fragments = fragments; }

}
