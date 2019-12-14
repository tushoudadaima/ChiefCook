package com.example.administrator.shixun.Entity;

public class Meterial {
    private String vname;
    private String count;

    public String getVname() {
        return vname;
    }

    public void setVname(String vname) {
        this.vname = vname;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public Meterial(String vname, String count) {
        this.vname = vname;
        this.count = count;
    }

    public Meterial() {

    }
}
