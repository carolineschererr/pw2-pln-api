package org.acme.control;

import java.util.Date;

public class CreateUser {

private String username;
private String email;
private String password;
private String firstName;
private String lastName;
private Date birthDate;

public String getUsername() {
    return username;
}
public void setUsername(String username) {
    this.username = username;
}
public String getEmail() {
    return email;
}
public void setEmail(String email) {
    this.email = email;
}
public String getPassword() {
    return password;
}
public void setPassword(String password) {
    this.password = password;
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
public Date getBirthDate() {
    return birthDate;
}
public void setBirthDate(Date birthDate) {
    this.birthDate = birthDate;
}
    
}
