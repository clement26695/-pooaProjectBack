package com.centralesupelec.osy2018.myseries.models.dto;

public class UserDTO {

  private String login;
  private String password;

  /**
   * @return the login
   */
  public String getLogin() {
    return login;
  }

  /**
   * @return the password
   */
  public String getPassword() {
    return password;
  }

  /**
   * @param password the password to set
   */
  public void setPassword(String password) {
    this.password = password;
  }

  /**
   * @param login the login to set
   */
  public void setLogin(String login) {
    this.login = login;
  }

}