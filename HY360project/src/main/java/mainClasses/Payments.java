package mainClasses;

public class Payments {
    int bill_id,emp_id;
    double amount, family_allowance, research_allowance, lib_allowance;
    String date,employee_category;

    public Payments( int emp_id, String employee_category, double amount, double family_allowance, double research_allowance, double lib_allowance, String date) {
        this.emp_id = emp_id;
        this.employee_category = employee_category;
        this.date = date;
        this.amount = amount;
        this.family_allowance = family_allowance;
        this.research_allowance = research_allowance;
        this.lib_allowance = lib_allowance;
    }

    public void setBillId(int bill_id){this.bill_id=bill_id;}
    public void setAmount(double amount){this.amount=amount;}
    public void setEmpId(int emp_id){this.emp_id=emp_id;}
    public void setDate(String date){this.date=date;}
    public void setEmployeeCategory(String emp_category){this.employee_category=emp_category;}
    public void setResearch_allowance(double research_allowance){this.research_allowance=research_allowance;}
    public void setLib_allowance(double lib_allowance) {this.lib_allowance = lib_allowance; }
    public void setFamily_allowance(double family_allowance) { this.family_allowance = family_allowance; }

    public int getBillId(){return this.bill_id;}
    public double getAmount(){return this.amount;}
    public int getEmpId(){return this.emp_id;}
    public String getDate(){return this.date;}
    public String getEmployeeCategory(){return this.employee_category;}
    public double getFamily_allowance() {return family_allowance; }
    public double getLib_allowance() {return lib_allowance; }
    public double getResearch_allowance() {return research_allowance;}

    @Override
    public String toString() {
        return "Payments{" +
                "bill_id=" + bill_id +
                ", emp_id=" + emp_id +
                ", amount=" + amount +
                ", family_allowance=" + family_allowance +
                ", research_allowance=" + research_allowance +
                ", lib_allowance=" + lib_allowance +
                ", date='" + date + '\'' +
                ", employee_category='" + employee_category + '\'' +
                '}';
    }
}

