package dto;

public class UserDto {
     private String userName;
     private String name;
     private String Address;
     private String teleNumber;
     private String Role;
    private String userPassword;

    public UserDto() {
    }

    public UserDto(String userName, String userPassword) {
        this.setUserName(userName);
        this.setUserPassword(userPassword);
    }

    public UserDto(String userName, String name, String address, String teleNumber, String role, String userPassword) {
        this.setUserName(userName);
        this.setName(name);
        this.setAddress(address);
        this.setTeleNumber(teleNumber);
        this.setRole(role);
        this.setUserPassword(userPassword);
    }


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getTeleNumber() {
        return teleNumber;
    }

    public void setTeleNumber(String teleNumber) {
        this.teleNumber = teleNumber;
    }

    public String getRole() {
        return Role;
    }

    public void setRole(String role) {
        Role = role;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }


}
