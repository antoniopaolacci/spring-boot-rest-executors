package it.example.app.restbean.soundapp;

public class SoundappResponse {

	private String summary;
	private String description;

	public SoundappResponse() {
		super();
	}

	public SoundappResponse(String summary, String description) {
		super();
		this.summary = summary;
		this.description = description;
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	
	@Override
	public String toString() {
		return "SoundappResponse [summary=" + summary + ", description=" + description + "]";
	}
	
}
