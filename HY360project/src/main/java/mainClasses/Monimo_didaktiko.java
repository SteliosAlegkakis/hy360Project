package mainClasses;

public class Monimo_didaktiko extends Monimo_proswpiko{
    double research_allowance;

    public Monimo_didaktiko(int perm_id,String start_date,int children_num,String children_ages,String marital_status) {
        //the basic salary for all employees in category monimo_didaktiko is 1200 and research allowance 200
        super(perm_id,1200,start_date,children_num,children_ages,marital_status);
        this.research_allowance=200;
    }
    public void setResearchAllowance(int research_allowance){this.research_allowance=research_allowance;}

    public double getResearchAllowance(){return this.research_allowance;}
}
