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
        else if(request_type.equals("update")) updateEmployee(request,response);
        else if(request_type.equals("fire")) fire(request,response);
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
            int temp_id = ypallilos.getEmpID();

            String exp_date = request.getParameter("exp_date");
            double salary = Double.parseDouble(request.getParameter("salary"));

            if(ypallilos.getCategory().equals("symvasiouxo_didaktiko")) {
                Symvasiouxo_didaktiko sd = new Symvasiouxo_didaktiko(temp_id, salary, ypallilos.getMaritalStatus(),ypallilos.getChildrenNum(), ypallilos.getChildrenAges(),exp_date);
                EditSymvasiouxoDidaktikoTable.createNewDatabaseEntry(sd);
            }
            else if(ypallilos.getCategory().equals("symvasiouxo_dioikitiko")){
                Symvasiouxo_dioikitiko sd = new Symvasiouxo_dioikitiko(temp_id, salary, ypallilos.getMaritalStatus(),ypallilos.getChildrenNum(), ypallilos.getChildrenAges(),exp_date);
                EditSymvasiouxoDioikitikoTable.createNewDatabaseEntry(sd);
            }

            response.setStatus(200);
        }
        catch (SQLException e) {
            System.err.println("SQLException at hire() in servlet");
            response.setStatus(500);
        }

    }

    private void updateEmployee(HttpServletRequest request, HttpServletResponse response){
        System.out.println("update");

        try {
            Ypallilos ypallilos = EditYpallilosTable.jsonToObject(EditYpallilosTable.databaseToJSON(request.getParameter("name")));

            if(request.getParameter("address")!=null) ypallilos.setAddress(request.getParameter("address"));
            if(request.getParameter("phone")!=null) ypallilos.setPhone(request.getParameter("phone"));
            if(request.getParameter("dept")!=null) ypallilos.setDept(request.getParameter("dept"));
            if(request.getParameter("IBAN")!=null) ypallilos.setBank(request.getParameter("IBAN"));
            if(request.getParameter("bank")!=null) ypallilos.setIBAN(request.getParameter("bank"));
            if(request.getParameter("marital_status")!=null) ypallilos.setMaritalStatus(request.getParameter("marital_status"));
            if(request.getParameter("children_num")!=null) ypallilos.setChildrenNum(Integer.parseInt(request.getParameter("children_num")));
            if(request.getParameter("children_ages")!=null) ypallilos.setChildrenAges(request.getParameter("children_ages"));

            String category = ypallilos.getCategory();
            int emp_id = ypallilos.getEmpID();

            if(category.equals("monimo_didaktiko")) {
                EditMonimoDidaktikoTable.delete(emp_id);
                Monimo_didaktiko md = new Monimo_didaktiko(emp_id, ypallilos.getStartDate(),ypallilos.getChildrenNum(), ypallilos.getChildrenAges(),ypallilos.getMaritalStatus());
                EditMonimoDidaktikoTable.createNewDatabaseEntry(md);
                EditYpallilosTable.update(ypallilos);
                response.setStatus(200);
            }
            else if(category.equals("monimo_dioikitiko")){
                EditMonimoDioikitikoTable.delete(emp_id);
                Monimo_dioikitiko md = new Monimo_dioikitiko(emp_id, ypallilos.getStartDate(),ypallilos.getChildrenNum(), ypallilos.getChildrenAges(),ypallilos.getMaritalStatus());
                EditMonimoDioikitikoTable.createNewDatabaseEntry(md);
                EditYpallilosTable.update(ypallilos);
                response.setStatus(200);
            }
            else if(category.equals("symvasiouxo_didaktiko")){
                Symvasiouxo_didaktiko old = EditSymvasiouxoDidaktikoTable.jsonToObject(EditSymvasiouxoDidaktikoTable.databaseToJSON(emp_id));
                EditSymvasiouxoDidaktikoTable.delete(emp_id);
                Symvasiouxo_didaktiko sd = new Symvasiouxo_didaktiko(emp_id, old.getSalary(), ypallilos.getMaritalStatus(), ypallilos.getChildrenNum(), ypallilos.getChildrenAges(), old.getExpDate());
                EditSymvasiouxoDidaktikoTable.createNewDatabaseEntry(sd);
                EditYpallilosTable.update(ypallilos);
                response.setStatus(200);
            }
            else if(category.equals("symvasiouxo_dioikitiko")){
                Symvasiouxo_dioikitiko old = EditSymvasiouxoDioikitikoTable.jsonToObject(EditSymvasiouxoDioikitikoTable.databaseToJSON(emp_id));
                EditSymvasiouxoDioikitikoTable.delete(emp_id);
                Symvasiouxo_dioikitiko sd = new Symvasiouxo_dioikitiko(emp_id, old.getSalary(), ypallilos.getMaritalStatus(), ypallilos.getChildrenNum(), ypallilos.getChildrenAges(), old.getExpDate());
                EditSymvasiouxoDioikitikoTable.createNewDatabaseEntry(sd);
                EditYpallilosTable.update(ypallilos);
                response.setStatus(200);
            }
            else response.setStatus(500);

        } catch (SQLException e) {
            System.err.println("SQLException at updateEmployee()");
            response.setStatus(500);
        }
    }

    private void changeSalaries(){
        System.out.println("Change Salaries");
    }

    private void fire(HttpServletRequest request, HttpServletResponse response){
        System.out.println("Fire");
        try{
            int emp_id = Integer.parseInt(request.getParameter("emp_id"));
            String name = request.getParameter("name");
            Ypallilos ypallilos = EditYpallilosTable.jsonToObject(EditYpallilosTable.databaseToJSON(name));
            if(emp_id != ypallilos.getEmpID()) {
                response.setStatus(406);
                return;
            }
            String category = ypallilos.getCategory();

            EditYpallilosTable.delete(emp_id);
            if(category.equals("monimo_didaktiko")) EditMonimoDidaktikoTable.delete(emp_id);
            else if(category.equals("monimo_dioikitiko")) EditMonimoDioikitikoTable.delete(emp_id);
            else if(category.equals(("symvasiouxo_dioikitiko"))) EditSymvasiouxoDioikitikoTable.delete(emp_id);
            else if(category.equals("symvasiouxo_didaktiko")) EditSymvasiouxoDidaktikoTable.delete(emp_id);
            else response.setStatus(500);

            response.setStatus(200);
        }
        catch (SQLException e){
            System.err.println("SQLException at fire()");
            response.setStatus(500);
        }
    }

    private void payroll(){
        System.out.println("payroll");
    }

    private void getEmployee(){
        System.out.println("get user");
    }

    private void getCategory(){
        System.out.println("get category");
    }

    private void getStatistics(){
        System.out.println("statistics");
    }
}
