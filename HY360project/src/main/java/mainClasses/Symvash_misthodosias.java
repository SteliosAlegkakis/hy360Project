package mainClasses;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Symvash_misthodosias {
    int temp_id;
    double salary,family_allowance;
    String exp_date;

    public Symvash_misthodosias(double salary,String marital_status,int children_num,String children_ages,String exp_date){
        this.salary=salary;
        this.exp_date=exp_date;
        this.family_allowance=calculateFamilyAllowance(marital_status,children_num,children_ages,salary);
    }

    private double calculateFamilyAllowance(String marital_status,int children_num,String children_ages,double salary){

        if(!marital_status.equals("married")) return 0; //family allowance is 0 for unmarried employees

        List<String> ages = new ArrayList<>();
        double family_allowance = salary * 0.05; //5% for wife or husband

        StringTokenizer tokenizer = new StringTokenizer(children_ages,",");//ages are separated by ","
        while(tokenizer.hasMoreElements()) ages.add(tokenizer.nextToken());

        //if the ages provided are more or less than the children_num the input for the ages is invalid
        if(ages.size()>children_num || ages.size()<children_num) return family_allowance;

        for(int i=0;i<ages.size();i++){
            if(Integer.parseInt(ages.get(i))<18) family_allowance += salary*0.05;//5% for each child under 18
        }

        return family_allowance;
    }

    public void setTempId(int temp_id){this.temp_id=temp_id;}
    public void setFamilyAllowance(double fam_allowance){this.family_allowance=fam_allowance;}
    public void setSalary(double salary){this.salary=salary;}
    public void setExpDate(String exp_date){this.exp_date=exp_date;}

    public int getTempId(){return this.temp_id;}
    public double getFamilyAllowance(){return this.family_allowance;}
    public double getSalary(){return this.salary;}
    public String getExpDate(){return this.exp_date;}
}
