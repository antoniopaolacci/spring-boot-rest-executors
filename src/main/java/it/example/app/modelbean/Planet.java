package it.example.app.modelbean;

public class Planet {

	private String name;
	private int id;
	
	public Planet() { }
	
	public Planet(String n, int i) {
		this.name = n;
		this.id = i;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	@Override
	public String toString() {
		return "Planet [name=" + name + ", id=" + id + "]";
	}
	
	
}
