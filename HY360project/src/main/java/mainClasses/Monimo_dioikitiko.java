package mainClasses;

public class Monimo_dioikitiko {
    int perm_id,years;
    double salary,family_allowance;

    public Monimo_dioikitiko(int perm_id, int years, double salary, double family_allowance) {
        this.perm_id = perm_id;
        this.years = years;
        this.salary = salary;
        this.family_allowance = family_allowance;
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
