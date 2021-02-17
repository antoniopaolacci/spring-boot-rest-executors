package it.example.app.modelbean.soundapp;

public class StatsSoundappInfo {
	
	private int numSongEnqueued;
	private String nextSong;
	private String previousSong;
	private String[] historyPlayedSongs;
	
	public StatsSoundappInfo() {
		super();
	}

	public StatsSoundappInfo(int numSongEnqueued, String nextSong, String previousSong, String[] historyPlayedSongs) {
		super();
		this.numSongEnqueued = numSongEnqueued;
		this.nextSong = nextSong;
		this.previousSong = previousSong;
		this.historyPlayedSongs = historyPlayedSongs;
	}
	
	public int getNumSongEnqueued() {
		return numSongEnqueued;
	}
	public void setNumSongEnqueued(int numSongEnqueued) {
		this.numSongEnqueued = numSongEnqueued;
	}
	public String getNextSong() {
		return nextSong;
	}
	public void setNextSong(String nextSong) {
		this.nextSong = nextSong;
	}
	public String getPreviousSong() {
		return previousSong;
	}
	public void setPreviousSong(String previousSong) {
		this.previousSong = previousSong;
	}
	public String[] getHistoryPlayedSongs() {
		return historyPlayedSongs;
	}
	public void setHistoryPlayedSongs(String[] historyPlayedSongs) {
		this.historyPlayedSongs = historyPlayedSongs;
	}
	
	

}
