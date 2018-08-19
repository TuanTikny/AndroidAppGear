package com.tuanbapk.appgear.Model;

public class Oder {

    private int id ;
    private String jsonproducts;
    private String jsonuser;
    private Float totalprice;
    private String date;
    private int status;

    public Oder(int id, String jsonproducts, String jsonuser, Float totalprice, String date, int status) {
        this.id = id;
        this.jsonproducts = jsonproducts;
        this.jsonuser = jsonuser;
        this.totalprice = totalprice;
        this.date = date;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getJsonproducts() {
        return jsonproducts;
    }

    public void setJsonproducts(String jsonproducts) {
        this.jsonproducts = jsonproducts;
    }

    public String getJsonuser() {
        return jsonuser;
    }

    public void setJsonuser(String jsonuser) {
        this.jsonuser = jsonuser;
    }

    public Float getTotalprice() {
        return totalprice;
    }

    public void setTotalprice(Float totalprice) {
        this.totalprice = totalprice;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
