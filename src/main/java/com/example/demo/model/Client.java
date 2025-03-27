package com.example.demo.model;

public class Client {
	private String clientNo;
    private String fname;
    private String lname;
    private String telno;
    private String street;
    private String city;
    private String email;
    private String preftype;
    private int maxrent;

    // Getters and Setters
    public String getClientNo() { return clientNo; }
    public void setClientNo(String clientNo) { this.clientNo = clientNo; }

    public String getFname() { return fname; }
    public void setFname(String fname) { this.fname = fname; }

    public String getLname() { return lname; }
    public void setLname(String lname) { this.lname = lname; }

    public String getTelno() { return telno; }
    public void setTelno(String telno) { this.telno = telno; }

    public String getStreet() { return street; }
    public void setStreet(String street) { this.street = street; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPreftype() { return preftype; }
    public void setPreftype(String preftype) { this.preftype = preftype; }

    public int getMaxrent() { return maxrent; }
    public void setMaxrent(int maxrent) { this.maxrent = maxrent; }

}
