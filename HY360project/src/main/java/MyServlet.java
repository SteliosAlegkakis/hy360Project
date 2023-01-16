import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.*;
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
        else if(request_type.equals("hire")) {hire(request,response);}
        else if(request_type.equals("contract")) contract();
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
            Ypallilos ypallilos = EditYpallilosTable.ypallilosFromJs(request);
            EditYpallilosTable.createNewYpallilos(ypallilos);

            String name = ypallilos.getName();
            ypallilos = EditYpallilosTable.jsonToYpallilos(EditYpallilosTable.databaseYpallilosToJSON(name));


            if(ypallilos.getCategory().equals("monimo_didaktiko")) {
                Monimo_didaktiko md = new Monimo_didaktiko(ypallilos.getEmpID(), ypallilos.getStartDate(),ypallilos.getChildrenNum(), ypallilos.getChildrenAges(),ypallilos.getMaritalStatus());
                EditMonimoDidaktikoTable.createNewMonimoDidaktiko(md);
            }
            else if(ypallilos.getCategory().equals("monimo_dioikitiko")){
                Monimo_dioikitiko md = new Monimo_dioikitiko(ypallilos.getEmpID(), ypallilos.getStartDate(),ypallilos.getChildrenNum(), ypallilos.getChildrenAges(),ypallilos.getMaritalStatus());
                EditMonimoDioikitikoTable.createNewMonimoDioikitiko(md);
            }

            response.setStatus(200);
        }
        catch (SQLException e) {
            System.err.println("SQLException at hire() in servlet");
            response.setStatus(500);
        }
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
