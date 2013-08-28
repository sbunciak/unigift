package cz.muni.fi.pv207.unigift.payrolls;

import java.io.Serializable;

public class Payroll implements Serializable {

	private static final long serialVersionUID = -422294992829597395L;

	private Integer hours;
	private Integer hourPay;
	private Integer pay;
	private Employee employee;
	
	public Payroll(Integer hours, Integer hourPay, Integer pay, Employee employee) {
        super();
        this.hours = hours;
        this.hourPay = hourPay;
        this.pay = pay;
        this.employee = employee;
    }
    public Integer getHours() {
        return hours;
    }
    public void setHours(Integer hours) {
        this.hours = hours;
    }
    public Integer getHourPay() {
        return hourPay;
    }
    public void setHourPay(Integer hourPay) {
        this.hourPay = hourPay;
    }
    public Integer getPay() {
        return pay;
    }
    public void setPay(Integer pay) {
        this.pay = pay;
    }
    public Employee getEmployee() {
        return employee;
    }
    public void setEmployee(Employee employee) {
        this.employee = employee;
    } 
	
	@Override
	public String toString() {
	    return "Payroll for employee " + employee.getName() + ", pay: " + pay;
	}
}
