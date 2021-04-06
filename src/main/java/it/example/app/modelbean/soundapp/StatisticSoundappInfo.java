package it.example.app.modelbean.soundapp;

import java.util.LinkedList;

public class StatisticSoundappInfo {

	private int count;
	private String now;
	private String next;
	private String prev;
	private LinkedList<String> history;

	public StatisticSoundappInfo(int count, String now, String next, String prev) {
		super();
		this.count = count;
		this.now = now;
		this.next = next;
		this.prev = prev;
	}

	public StatisticSoundappInfo() {
		super();
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getNow() {
		return now;
	}

	public void setNow(String now) {
		this.now = now;
	}

	public String getNext() {
		return next;
	}

	public void setNext(String next) {
		this.next = next;
	}

	public String getPrev() {
		return prev;
	}

	public void setPrev(String prev) {
		this.prev = prev;
	}

	public LinkedList<String> getHistory() {
		return history;
	}

	public void setHistory(LinkedList<String> history) {
		this.history = history;
	}

	// safety add item to the history list 
	public boolean addHistoryItem(String item) {
		//safety
		if(this.history==null) {
			this.history = new LinkedList<String>();
		}
		return history.add(item);
	}

	@Override
	public String toString() {
		return "StatisticSoundappInfo [count=" + count + ", now=" + now + ", next=" + next + ", prev=" + prev + "]";
	}

}
