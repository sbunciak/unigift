package cz.muni.fi.pv207.unigift.payrolls;

public class PayrollService {

	public void addEmployee(Employee e) {
		System.out.println("Adding " + e + "to UniGift Payroll system.");
	}
	
	public void pay(Payroll p) {
        System.out.println("Executing electronical payment of " + p);
    }
	
}
