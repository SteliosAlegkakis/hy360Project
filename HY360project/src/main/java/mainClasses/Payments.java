package mainClasses;

public class Payments {
    int bill_id,emp_id;
    double amount;
    String date,emp_category;

    public void setBillId(int bill_id){this.bill_id=bill_id;}
    public void setAmount(double amount){this.amount=amount;}
    public void setEmpId(int emp_id){this.emp_id=emp_id;}
    public void setDate(String date){this.date=date;}
    public void setEmpCategory(String emp_category){this.emp_category=emp_category;}

    public int getBillId(){return this.bill_id;}
    public double getAmount(){return this.amount;}
    public int getEmpId(){return this.emp_id;}
    public String getDate(){return this.date;}
    public String getEmpCategory(){return this.emp_category;}
}
