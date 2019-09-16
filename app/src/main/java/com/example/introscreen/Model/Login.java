package com.example.introscreen.Model;

public class Login {
    private String U_name;
    private String u_id;
    private String u_password;
    private String u_type;

    public Login() {
    }

    public Login(String u_name, String u_id, String u_password, String u_type) {
        U_name = u_name;
        this.u_id = u_id;
        this.u_password = u_password;
        this.u_type = u_type;
    }

    public String getU_name() {
        return U_name;
    }

    public void setU_name(String u_name) {
        U_name = u_name;
    }

    public String getU_id() {
        return u_id;
    }

    public void setU_id(String u_id) {
        this.u_id = u_id;
    }

    public String getU_password() {
        return u_password;
    }

    public void setU_password(String u_password) {
        this.u_password = u_password;
    }

    public String getU_type() {
        return u_type;
    }

    public void setU_type(String u_type) {
        this.u_type = u_type;
    }
}
