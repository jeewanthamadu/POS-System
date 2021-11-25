package entity;

public class User {
    private String user_Name;
    private String name;
    private String Address;
    private String teleNo;
    private String Role;
    private String user_Password;

    public User(String text, String txtPasswordText) {
    }

    public User(String user_Name, String name, String address, String teleNo, String role, String user_Password) {
        this.user_Name = user_Name;
        this.name = name;
        Address = address;
        this.teleNo = teleNo;
        Role = role;
        this.user_Password = user_Password;
    }

    public String getUser_Name() {
        return user_Name;
    }

    public void setUser_Name(String user_Name) {
        this.user_Name = user_Name;
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

    public String getTeleNo() {
        return teleNo;
    }

    public void setTeleNo(String teleNo) {
        this.teleNo = teleNo;
    }

    public String getRole() {
        return Role;
    }

    public void setRole(String role) {
        Role = role;
    }

    public String getUser_Password() {
        return user_Password;
    }

    public void setUser_Password(String user_Password) {
        this.user_Password = user_Password;
    }
}
