package it.example.app.restbean.soundapp.login;

public class MyLoginBeanResponse {
	
	public String firstName;
    public String lastName;
    public String userID;
    public String password;
    public String uniqueID;
    
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

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUniqueID() {
		return uniqueID;
	}

	public void setUniqueID(String uniqueID) {
		this.uniqueID = uniqueID;
	}

	@Override
	public String toString() {
		return "MyLoginBeanResponse [firstName=" + firstName + ", lastName=" + lastName + ", userID=" + userID
				+ ", password=" + password + ", uniqueID=" + uniqueID + "]";
	}

}