package it.example.app.restbean.planet;

public class PlanetUri {

	// Path params
	private String planet;
	private String moon;
	
	// Query params
	private String firstName;
	private String lastName;
	
	
	public String getPlanet() {
		return planet;
	}
	public void setPlanet(String planet) {
		this.planet = planet;
	}
	
	public String getMoon() {
		return moon;
	}
	public void setMoon(String moon) {
		this.moon = moon;
	}
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	@Override
	public String toString() {
		return "PlanetUri [planet=" + planet + ", moon=" + moon + ", firstName=" + firstName + ", lastName=" + lastName
				+ "]";
	}
	
	
	
}
