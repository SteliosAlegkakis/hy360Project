import javax.servlet.annotation.*;
import java.sql.SQLException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.*;
import mainClasses.*;

@WebServlet(name = "MyServlet", value = "/MyServlet")
public class MyServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        String request_type = request.getParameter("request");
        if(request_type.equals("change_salaries")) changeSalaries();
        else if(request_type.equals("hire")) {hire(request,response);}
        else if(request_type.equals("contract")) contract(request,response);
        else if(request_type.equals("update_employee")) updateEmployee();
        else if(request_type.equals("fire")) fire();
        else if(request_type.equals("payroll")) payroll();
        else {
            response.setStatus(406);
            System.out.println("Error! Request type is incorrect!There is no "+request_type+" request");
        }
    }

    private void hire(HttpServletRequest request, HttpServletResponse response) {

        System.out.println("hire");

        try{
            Ypallilos ypallilos = EditYpallilosTable.objectFromJs(request);
            EditYpallilosTable.createNewDatabaseEntry(ypallilos);

            String name = ypallilos.getName();
            ypallilos = EditYpallilosTable.jsonToObject(EditYpallilosTable.databaseToJSON(name));


            if(ypallilos.getCategory().equals("monimo_didaktiko")) {
                Monimo_didaktiko md = new Monimo_didaktiko(ypallilos.getEmpID(), ypallilos.getStartDate(),ypallilos.getChildrenNum(), ypallilos.getChildrenAges(),ypallilos.getMaritalStatus());
                EditMonimoDidaktikoTable.createNewDatabaseEntry(md);
            }
            else if(ypallilos.getCategory().equals("monimo_dioikitiko")){
                Monimo_dioikitiko md = new Monimo_dioikitiko(ypallilos.getEmpID(), ypallilos.getStartDate(),ypallilos.getChildrenNum(), ypallilos.getChildrenAges(),ypallilos.getMaritalStatus());
                EditMonimoDioikitikoTable.createNewDatabaseEntry(md);
            }

            response.setStatus(200);
        }
        catch (SQLException e) {
            System.err.println("SQLException at hire() in servlet");
            response.setStatus(500);
        }
    }

    private void contract(HttpServletRequest request, HttpServletResponse response){
        System.out.println("contract");

        try{
            Ypallilos ypallilos = EditYpallilosTable.objectFromJs(request);
            EditYpallilosTable.createNewDatabaseEntry(ypallilos);

            String name = ypallilos.getName();
            ypallilos = EditYpallilosTable.jsonToObject(EditYpallilosTable.databaseToJSON(name));

            String exp_date = request.getParameter("exp_date");
            double salary = Double.parseDouble(request.getParameter("salary"));

            if(ypallilos.getCategory().equals("symvasiouxo_didaktiko")) {
                Symvasiouxo_didaktiko sd = new Symvasiouxo_didaktiko(salary, ypallilos.getMaritalStatus(),ypallilos.getChildrenNum(), ypallilos.getChildrenAges(),exp_date);
                EditSymvasiouxoDidaktikoTable.createNewDatabaseEntry(sd);
            }
            else if(ypallilos.getCategory().equals("symvasiouxo_dioikitiko")){
                Symvasiouxo_dioikitiko sd = new Symvasiouxo_dioikitiko(salary, ypallilos.getMaritalStatus(),ypallilos.getChildrenNum(), ypallilos.getChildrenAges(),exp_date);
                EditSymvasiouxoDioikitikoTable.createNewDatabaseEntry(sd);
            }

            response.setStatus(200);
        }
        catch (SQLException e) {
            System.err.println("SQLException at hire() in servlet");
            response.setStatus(500);
        }

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
