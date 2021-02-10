package it.example.app.modelbean.soundapp;

public class SoundappInfo {

	private String summary;
	private String description;

	public SoundappInfo() {
		super();
	}

	public SoundappInfo(String summary, String description) {
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
		return "SoundappInfo [summary=" + summary + ", description=" + description + "]";
	}

}
