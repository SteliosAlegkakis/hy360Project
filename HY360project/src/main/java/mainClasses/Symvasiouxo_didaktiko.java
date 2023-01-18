package mainClasses;

public class Symvasiouxo_didaktiko extends Symvash_misthodosias{
    double lib_allowance;

    public Symvasiouxo_didaktiko(int temp_id,double salary, String marital_status, int children_num, String children_ages, String exp_date) {
        super(temp_id,salary, marital_status, children_num, children_ages,exp_date);
        this.lib_allowance=200;
    }

    public void setLibAllowance(double lib_allowance){this.lib_allowance=lib_allowance;}
    public double getLibAllowance(){return this.lib_allowance;}

}
