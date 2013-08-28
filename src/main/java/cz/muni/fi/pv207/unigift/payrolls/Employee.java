package cz.muni.fi.pv207.unigift.payrolls;

import java.io.Serializable;

public class Employee implements Serializable {

    private static final long serialVersionUID = 2592198133430350473L;

    private String name;

    private Integer id;

    private Boolean payInCash;
    
    public Employee() {
        this("", 0, false);
    }

    public Employee(String name, Integer id, Boolean payInCash) {
        super();
        this.name = name;
        this.id = id;
        this.payInCash = payInCash;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getPayInCash() {
        return payInCash;
    }

    public void setPayInCash(Boolean payInCash) {
        this.payInCash = payInCash;
    }

    @Override
    public String toString() {
        return "Employee " + name + "(ID: " + id + ")";
    }
}
