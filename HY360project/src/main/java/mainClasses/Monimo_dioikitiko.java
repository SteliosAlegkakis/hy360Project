package mainClasses;

public class Monimo_dioikitiko extends Monimo_proswpiko{
    public Monimo_dioikitiko(int perm_id, String start_date,int children_num, String children_ages,String marital_status) {
        //the basic salary for all employees in category monimo_dioikitiko is 1500
        super(perm_id,1500,start_date,children_num,children_ages,marital_status);
    }
}
