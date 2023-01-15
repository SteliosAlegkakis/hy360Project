package mainClasses;

public class Monimo_didaktiko {
    int perm_id,years;
    double salary,research_allowance,family_allowance;

    public Monimo_didaktiko(int perm_id, int years, double salary, double family_allowance, double researchAllowance) {
        this.perm_id = perm_id;
        this.salary = salary;
        this.years = years;
        this.family_allowance =  family_allowance;
        this.research_allowance = research_allowance;
    }

    public void setPermId(int perm_id_id){this.perm_id=perm_id;}
    public void setYears(int years){this.years=years;}
    public void setSalary(double salary){this.salary=salary;}
    public void setResearchAllowance(int research_allowance){this.research_allowance=research_allowance;}
    public void setFamily_allowance(double family_allowance){this.family_allowance=family_allowance;}

    public int getPermId(){return this.perm_id;}
    public int getYears(){return this.years;}
    public double getSalary() {return this.salary;}
    public double getFamily_allowance(){return this.family_allowance;}
    public double getResearch_allowance(){return this.research_allowance;}
}
