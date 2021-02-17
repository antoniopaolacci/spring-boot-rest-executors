package it.example.app.restbean.soundapp;

public class StatsSoundappResponse {

	private int count;
	private String next;
	private String previous;
	private String[] history;
	
	public StatsSoundappResponse() {
		super();
	}

	public StatsSoundappResponse(int count, String next, String previous, String[] history) {
		super();
		this.count = count;
		this.next = next;
		this.previous = previous;
		this.history = history;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getNext() {
		return next;
	}

	public void setNext(String next) {
		this.next = next;
	}

	public String getPrevious() {
		return previous;
	}

	public void setPrevious(String previous) {
		this.previous = previous;
	}

	public String[] getHistory() {
		return history;
	}

	public void setHistory(String[] history) {
		this.history = history;
	}
	
}
