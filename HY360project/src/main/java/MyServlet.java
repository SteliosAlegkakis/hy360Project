import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import database.EditMonimoDidaktikoTable;
import database.EditMonimoDioikitikoTable;
import database.EditYpallilosTable;
import mainClasses.Monimo_didaktiko;
import mainClasses.Monimo_dioikitiko;
import mainClasses.Ypallilos;

@WebServlet(name = "MyServlet", value = "/MyServlet")
public class MyServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String request_type = request.getParameter("request");
        if(request_type.equals("get_employee")) getEmployee();
        else if(request_type.equals("get_category")) getCategory();
        else if(request_type.equals("get_statistics")) getStatistics();
        else {
            response.setStatus(406);
            System.out.println("Error! Request type is incorrect.");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String request_type = request.getParameter("request");
        if(request_type.equals("change_salaries")) changeSalaries();
        else if(request_type.equals("hire")) {
            try {
                hire(request,response);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else if(request_type.equals("contract")) contract();
        else if(request_type.equals("update_employee")) updateEmployee();
        else if(request_type.equals("fire")) fire();
        else if(request_type.equals("payroll")) payroll();
        else {
            response.setStatus(406);
            System.out.println("Error! Request type is incorrect!There is no "+request_type+" request");
        }
    }

    private void hire(HttpServletRequest request, HttpServletResponse response) throws Exception {

        System.out.println("hire");

        Ypallilos ypallilos = EditYpallilosTable.ypallilosFromJs(request);
        EditYpallilosTable.createNewYpallilos(ypallilos);

        String name = ypallilos.getName();
        ypallilos = EditYpallilosTable.jsonToYpallilos(EditYpallilosTable.databaseYpallilosToJSON(name));

        double salary = 0,family_allowance = 0;
        int emp_id = ypallilos.getEmpID();

        if(ypallilos.getCategory().equals("monimo_didaktiko")) {
            salary = 1200;
            double research_allowance = 200;
            if(ypallilos.getMaritalStatus().equals("married"))
                family_allowance = familyAllowance(ypallilos.getChildrenAges(),ypallilos.getChildrenNum(),salary);
            //TODO: 14/01/2023 make an entry at monimo_didaktiko table
            Monimo_didaktiko monimo_didaktiko = new Monimo_didaktiko(emp_id,0,salary,family_allowance,research_allowance);
            EditMonimoDidaktikoTable.createNewMonimoDidaktiko(monimo_didaktiko);
        }
        else if(ypallilos.getCategory().equals("monimo_dioikitiko")){
            salary = 1500;
            if(ypallilos.getMaritalStatus().equals("married"))
                family_allowance = familyAllowance(ypallilos.getChildrenAges(),ypallilos.getChildrenNum(),salary);
            // TODO: 14/01/2023 make an entry at monimo_dioikitiko
            Monimo_dioikitiko monimo_dioikitiko = new Monimo_dioikitiko(emp_id,0,salary,family_allowance);
            EditMonimoDioikitikoTable.createNewMonimoDioikitiko(monimo_dioikitiko);
        }
        System.out.println("Employee id:"+emp_id);
        System.out.println("Salary: "+salary);
        System.out.println("family allowance: "+family_allowance);

        response.setStatus(200);
    }

    private double familyAllowance(String children_ages,int childrenNum,double salary){
        List<String> ages = new ArrayList<>();
        double family_allowance = salary * 0.05;
        StringTokenizer tokenizer = new StringTokenizer(children_ages," ");

        while(tokenizer.hasMoreElements()) ages.add(tokenizer.nextToken());

        if(ages.size()>childrenNum||ages.size()<childrenNum) return family_allowance;

        for(int i=0;i<ages.size();i++){
            if(Integer.parseInt(ages.get(i))<18) family_allowance += salary*0.05;
        }

        return family_allowance;
    }

    private void contract(){
        System.out.println("contract");
        //TODO contract()
    }

    private void updateEmployee(){
        System.out.println("update");
        //TODO updateEmployee()
    }

    private void changeSalaries(){
        System.out.println("Change Salaries");
        //TODO changeSalaries()
    }

    private void fire(){
        System.out.println("Fire");
        //TODO fire()
    }

    private void payroll(){
        System.out.println("payroll");
        //TODO payroll()
    }

    private void getEmployee(){
        System.out.println("get user");
        //TODO getEmployee()
    }

    private void getCategory(){
        System.out.println("get category");
        //TODO getCategory()
    }

    private void getStatistics(){
        System.out.println("statistics");
        //TODO getStatistics()
    }
}
