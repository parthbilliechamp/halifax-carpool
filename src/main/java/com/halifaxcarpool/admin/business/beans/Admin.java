package com.halifaxcarpool.admin.business.beans;

public class Admin {
    public int adminId;
    public String userName;
    public String password;

    public Admin() {

    }
    public Admin(int adminId, String userName, String password) {
        this.adminId = adminId;
        this.userName = userName;
        this.password = password;
    }

    public int getAdminId() {
        return adminId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Admin{" +
                "adminId=" + adminId +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

}
