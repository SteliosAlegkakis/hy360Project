package mainClasses;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.StringTokenizer;

public class Monimo_proswpiko {
    int perm_id,years;
    double salary,family_allowance;

    public Monimo_proswpiko(int perm_id,double salary, String start_date,int children_num,String children_ages,String marital_status) {
        this.perm_id = perm_id;
        this.years = calculateYears(start_date);
        this.salary = calculateSalaryFromYears(salary);
        this.family_allowance = calculateFamilyAllowance(marital_status,children_num,children_ages,salary);
    }

    private int calculateYears(String start_date){
        int start_year = 2023;
        int now =  Calendar.getInstance().get(Calendar.YEAR);
        //if this doesn't work try : int now = Year.now().getValue();

        //gets the first token from the start_date that is the year (yyyy-mm-dd)
        StringTokenizer tokenizer = new StringTokenizer(start_date,"-");
        if(tokenizer.hasMoreElements()) start_year = Integer.parseInt(tokenizer.nextToken());

        return (now - start_year);
    }

    private double calculateSalaryFromYears(double salary){
        //the raise starts if the employee has more than 1 year experience
        if(this.years>1){
            //raise the basic salary by 15% for each year
            for(int i=this.years; i>0; i--) salary+=salary*0.15;
        }
        return salary; //if the employee has < 1 years of experience the salary has stayed the same
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

    public void setPermId(int perm_id){this.perm_id=perm_id;}
    public void setYears(int years){this.years=years;}
    public void setSalary(double salary){this.salary=salary;}
    public void setFamilyAllowance(double family_allowance){this.family_allowance=family_allowance;}

    public int getPermId(){return this.perm_id;}
    public int getYears(){return this.years;}
    public double getSalary(){return this.salary;}
    public double getFamilyAllowance(){return this.family_allowance;}
}
