package com.ovgu.informationretrieval.wikisearcher.core.model;

public class WikiDocument {

	private Long id;

	private String content;

	private String docTitle;

	private String summary;

	private String imageURL;

	private Float score;

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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.docTitle == null) ? 0 : this.docTitle.hashCode());
		result = prime * result + ((this.summary == null) ? 0 : this.summary.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (this.getClass() != obj.getClass())
			return false;
		WikiDocument other = (WikiDocument) obj;
		if (this.docTitle == null) {
			if (other.docTitle != null)
				return false;
		} else if (!this.docTitle.equals(other.docTitle))
			return false;
		if (this.summary == null) {
			if (other.summary != null)
				return false;
		} else if (!this.summary.equals(other.summary))
			return false;
		return true;
	}

}
