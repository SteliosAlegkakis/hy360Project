package mainClasses;

public class Ypallilos {
    int emp_ID,children_num;
    String name,address,phone,start_date,IBAN,bank,category,dept,marital_status,children_ages;

    public void setEmp_ID(int emp_ID){this.emp_ID=emp_ID;}
    public void setName(String name){this.name=name;}
    public void setAddress(String address){this.address=address;}
    public void setPhone(String phone){this.phone=phone;}
    public void setStartDate(String start_date){this.start_date=start_date;}
    public void setIBAN(String IBAN){this.IBAN=IBAN;}
    public void setBank(String bank){this.bank=bank;}
    public void setCategory(String category){this.category=category;}
    public void setDept(String dept){this.dept=dept;}
    public void setMaritalStatus(String marital_status){this.marital_status=marital_status;}
    public void setChildrenNum(int children_num){this.children_num=children_num;}
    public void setChildrenAges(String children_ages){this.children_ages=children_ages;}

    public int getEmpID(){return this.emp_ID;}
    public String getName(){return this.name;}
    public String getAddress(){return this.address;}
    public String getPhone(){return this.phone;}
    public String getStartDate(){return this.start_date;}
    public String getIBAN(){return this.IBAN;}
    public String getBank(){return this.bank;}
    public String  getCategory(){return this.category;}
    public String getDept(){return this.dept;}
    public String getMaritalStatus(){return this.marital_status;}
    public int getChildrenNum(){return this.children_num;}
    public String getChildrenAges(){return this.children_ages;}
}
