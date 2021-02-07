package it.example.app.restbean;


public class PlanetResponse {

	private String name;
	private String solarDistance;
	
	public PlanetResponse() { }
	
	public PlanetResponse(String name, String solarDistance) {
		super();
		this.name = name;
		this.solarDistance = solarDistance;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getSolarDistance() {
		return solarDistance;
	}
	public void setSolarDistance(String solarDistance) {
		this.solarDistance = solarDistance;
	}

	@Override
	public String toString() {
		return "PlanetResponse [name=" + name + ", solarDistance=" + solarDistance + "]";
	}
		
}
