package it.example.app.modelbean.soundapp;

public class PlaySoundappInfo {
	
	private String url;
	private String title;
	private int duration;
	
	public PlaySoundappInfo(String url, String title, int duration) {
		super();
		this.url = url;
		this.title = title;
		this.duration = duration;
	}

	public PlaySoundappInfo() {
		super();
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	@Override
	public String toString() {
		return "StatsSoundappInfo [url=" + url + ", title=" + title + ", duration=" + duration + "]";
	}

}
