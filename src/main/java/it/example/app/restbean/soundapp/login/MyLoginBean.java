package it.example.app.restbean.soundapp.login;

import java.io.Serializable;
import java.util.Objects;

/**
 * MyLoginBean
 */

public class MyLoginBean implements Serializable {
  
  private static final long serialVersionUID = 1L;

  private String username = null;
  private String password = null;

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class MyLoginBean {\n");
    sb.append("    username: ").append(username).append("\n");
    sb.append("    password: ").append(password).append("\n");
    sb.append("}");
    return sb.toString();
  }

}

