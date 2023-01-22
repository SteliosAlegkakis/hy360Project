import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
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
        if(request_type.equals("get_employee")) getEmployee(request,response);
        else if(request_type.equals("payroll")) payroll(request,response);
        else if(request_type.equals("get_category")) getCategory(request,response);
        else if(request_type.equals("get_statistics")) getStatistics(request,response);
        else if(request_type.equals("avg_increase")) avgIncrease(request,response);
        else if(request_type.equals("total")) total(request,response);
        else if(request_type.equals("query")) query(request,response);
        else {
            response.setStatus(406);
            System.out.println("Error! Request type is incorrect.");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        String request_type = request.getParameter("request");
        if(request_type.equals("change_salaries")) changeSalaries(request,response);
        else if(request_type.equals("hire")) {hire(request,response);}
        else if(request_type.equals("contract")) contract(request,response);
        else if(request_type.equals("update")) updateEmployee(request,response);
        else if(request_type.equals("fire")) fire(request,response);
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

    private void changeSalaries(HttpServletRequest request, HttpServletResponse response){
        System.out.println("Change Salaries");

        int amount = Integer.parseInt(request.getParameter("amount"));
        if(amount<0) {
            response.setStatus(500);
            return;
        }
        double percent = 1+amount/100.0;
        double updated;

        ArrayList<Monimo_didaktiko> m_didaktiko = EditMonimoDidaktikoTable.getAllEntrys();
        ArrayList<Monimo_dioikitiko> m_dioikitiko = EditMonimoDioikitikoTable.getAllEntrys();
        ArrayList<Symvasiouxo_didaktiko> s_didaktiko = EditSymvasiouxoDidaktikoTable.getAllEntrys();
        ArrayList<Symvasiouxo_dioikitiko> s_dioikitiko = EditSymvasiouxoDioikitikoTable.getAllEntrys();

        try{
            Connection con = DB_Connection.getConnection();
            Statement stmt = con.createStatement();

            if(request.getParameter("m_didaktiko_salary").equals("checked")){
                for(int i=0;i<m_didaktiko.size();i++){
                    updated = Math.floor(m_didaktiko.get(i).getSalary()*percent);
                    String query = "UPDATE monimo_didaktiko SET salary = "+updated+" WHERE perm_id = "+m_didaktiko.get(i).getPermId();
                    stmt.executeUpdate(query);
                }
            }
            if(request.getParameter("m_didaktiko_family").equals("checked")){
                for(int i=0;i<m_didaktiko.size();i++){
                    updated = Math.floor(m_didaktiko.get(i).getFamilyAllowance()*percent);
                    String query = "UPDATE monimo_didaktiko SET family_allowance = "+updated+" WHERE perm_id = "+m_didaktiko.get(i).getPermId();
                    stmt.executeUpdate(query);
                }
            }
            if(request.getParameter("research").equals("checked")){
                for(int i=0;i<m_didaktiko.size();i++){
                    updated = Math.floor(m_didaktiko.get(i).getResearchAllowance()*percent);
                    String query = "UPDATE monimo_didaktiko SET research_allowance = "+updated+" WHERE perm_id = "+m_didaktiko.get(i).getPermId();
                    stmt.executeUpdate(query);
                }
            }

            if(request.getParameter("m_dioikitiko_salary").equals("checked")){
                for(int i=0;i<m_didaktiko.size();i++){
                    updated = Math.floor(m_dioikitiko.get(i).getSalary()*percent);
                    String query = "UPDATE monimo_dioikitiko SET salary = "+updated+" WHERE perm_id = "+m_dioikitiko.get(i).getPermId();
                    stmt.executeUpdate(query);
                }
            }
            if(request.getParameter("m_dioikitiko_family").equals("checked")){
                for(int i=0;i<m_dioikitiko.size();i++){
                    updated = Math.floor(m_dioikitiko.get(i).getFamilyAllowance()*percent);
                    String query = "UPDATE monimo_dioikitiko SET family_allowance = "+updated+" WHERE perm_id = "+m_dioikitiko.get(i).getPermId();
                    stmt.executeUpdate(query);
                }
            }

            if(request.getParameter("s_didaktiko_salary").equals("checked")){
                for(int i=0;i<s_didaktiko.size();i++){
                    updated = Math.floor(s_didaktiko.get(i).getSalary()*percent);
                    String query = "UPDATE symvasiouxo_didaktiko SET salary = "+updated+" WHERE temp_id = "+s_didaktiko.get(i).getTempId();
                    stmt.executeUpdate(query);
                }
            }
            if(request.getParameter("s_didaktiko_family").equals("checked")){
                for(int i=0;i<s_didaktiko.size();i++){
                    updated = Math.floor(s_didaktiko.get(i).getFamilyAllowance()*percent);
                    String query = "UPDATE symvasiouxo_didaktiko SET family_allowance = "+updated+" WHERE temp_id = "+s_didaktiko.get(i).getTempId();
                    stmt.executeUpdate(query);
                }
            }
            if(request.getParameter("library").equals("checked")){
                for(int i=0;i<s_didaktiko.size();i++){
                    updated = Math.floor(s_didaktiko.get(i).getLibAllowance()*percent);
                    String query = "UPDATE symvasiouxo_didaktiko SET lib_allowance = "+updated+" WHERE temp_id = "+s_didaktiko.get(i).getTempId();
                    stmt.executeUpdate(query);
                }
            }

            if(request.getParameter("s_dioikitiko_salary").equals("checked")){
                for(int i=0;i<s_dioikitiko.size();i++){
                    updated = Math.floor(s_dioikitiko.get(i).getSalary()*percent);
                    String query = "UPDATE symvasiouxo_dioikitiko SET salary = "+updated+" WHERE temp_id = "+s_dioikitiko.get(i).getTempId();
                    stmt.executeUpdate(query);
                }
            }
            if(request.getParameter("s_dioikitiko_family").equals("checked")){
                for(int i=0;i<s_dioikitiko.size();i++){
                    updated = Math.floor(s_dioikitiko.get(i).getFamilyAllowance()*percent);
                    String query = "UPDATE symvasiouxo_dioikitiko SET family_allowance = "+updated+" WHERE temp_id = "+s_dioikitiko.get(i).getTempId();
                    stmt.executeUpdate(query);
                }
            }

            response.setStatus(200);
        }
        catch (SQLException e) {
            System.err.println("SQLException at changeSalaries()");
            response.setStatus(500);
        } catch (ClassNotFoundException e) {
            System.err.println("ClassNotFoundException at changeSalaries()");
            response.setStatus(500);
        }
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

    private void payroll(HttpServletRequest request, HttpServletResponse response){
        System.out.println("payroll");
        try{
            ArrayList<Ypallilos> ypalliloi = EditYpallilosTable.getEmployees();
            String date = request.getParameter("date");

            for(int i=0; i<ypalliloi.size(); i++){
                Ypallilos ypallilos = ypalliloi.get(i);
                String category = ypallilos.getCategory();
                int emp_id = ypallilos.getEmpID();

                if(category.equals("monimo_didaktiko")){
                    Monimo_didaktiko md = EditMonimoDidaktikoTable.ObjectFromDatabase(emp_id);
                    Payments payment = new Payments(emp_id,category,md.getSalary(),md.getFamilyAllowance(),md.getResearchAllowance(),0,date);
                    EditPaymentsTable.createNewDatabaseEntry(payment);
                }
                else if(category.equals("monimo_dioikitiko")){
                    Monimo_dioikitiko md = EditMonimoDioikitikoTable.monimoDioikitikoFromDatabase(emp_id);
                    Payments payment = new Payments(emp_id,category,md.getSalary(),md.getFamilyAllowance(),0,0,date);
                    EditPaymentsTable.createNewDatabaseEntry(payment);
                }
                else if(category.equals("symvasiouxo_didaktiko")){
                    Symvasiouxo_didaktiko sd = EditSymvasiouxoDidaktikoTable.jsonToObject(EditSymvasiouxoDidaktikoTable.databaseToJSON(emp_id));
                    Payments payment = new Payments(emp_id,category,sd.getSalary(),sd.getFamilyAllowance(),0,sd.getLibAllowance(),date);
                    EditPaymentsTable.createNewDatabaseEntry(payment);
                }
                else if(category.equals("symvasiouxo_dioikitiko")){
                    Symvasiouxo_dioikitiko sd = EditSymvasiouxoDioikitikoTable.jsonToObject(EditSymvasiouxoDioikitikoTable.databaseToJSON(emp_id));
                    Payments payment = new Payments(emp_id,category,sd.getSalary(),sd.getFamilyAllowance(),0,0,date);
                    EditPaymentsTable.createNewDatabaseEntry(payment);
                }
                else{
                    response.setStatus(500);
                }
            }
            PrintWriter out = response.getWriter();

            ArrayList<Payments> md = EditPaymentsTable.getPaymentInfo("monimo_didaktiko");
            out.println("Κατάσταση μισθοδοσίας μόνιμου διδακτικού προσωπικού<br><br>");
            for(int i=0;i<md.size();i++) out.println(md.get(i).toHTMLString()+"<br><br>");

            md = EditPaymentsTable.getPaymentInfo("monimo_dioikitiko");
            out.println("<br>Κατάσταση μισθοδοσίας μόνιμου διοικιτικού προσωπικού<br><br>");
            for(int i=0;i<md.size();i++) out.println(md.get(i).toHTMLString()+"<br><br>");

            ArrayList<Payments> sd = EditPaymentsTable.getPaymentInfo("symvasiouxo_didaktiko");
            out.println("<br>Κατάσταση μισθοδοσίας συμβασιούχου διδακτικού προσωπικού<br><br>");
            for(int i=0;i<sd.size();i++) out.println(sd.get(i).toHTMLString()+"<br><br>");

            sd = EditPaymentsTable.getPaymentInfo("symvasiouxo_dioikitiko");
            out.println("<br>Κατάσταση μισθοδοσίας συμβασιούχου διοικιτικού προσωπικού<br><br>");
            for(int i=0;i<sd.size();i++) out.println(sd.get(i).toHTMLString()+"<br><br>");

        } catch (SQLException e) {
            System.err.println("SQLException at payroll()");
            response.setStatus(500);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void getEmployee(HttpServletRequest request, HttpServletResponse response){
        try{
            System.out.println("get user");

            PrintWriter out = response.getWriter();
            String name = request.getParameter("name");
            Ypallilos ypallilos = EditYpallilosTable.jsonToObject(EditYpallilosTable.databaseToJSON(name));

            out.println(ypallilos.toHTMLString());

            out.println("<h3>Μισθοδοσία Υπαλλήλου</h3><br><br>");
            int emp_id = ypallilos.getEmpID();
            ArrayList<Payments> payments = EditPaymentsTable.getPaymentsFromID(emp_id);
            for(int i=0;i<payments.size();i++) out.println(payments.get(i).toHTMLString()+"<br><br>");

            response.setStatus(200);
        } catch (SQLException e) {
            System.err.println("SQLException at get_employee()");
            response.setStatus(500);
        } catch (IOException e) {
            System.err.println("IOException at getEmployee()");
            response.setStatus(500);
        }
    }

    private void getCategory(HttpServletRequest request, HttpServletResponse response){
        System.out.println("get category");
        try{
            PrintWriter out = response.getWriter();
            String category = request.getParameter("category");
            ArrayList<Payments> payments = EditPaymentsTable.getPaymentInfo(category);

            for(int i=0;i<payments.size();i++) out.println(payments.get(i).toHTMLString()+"<br><br>");

            response.setStatus(200);

        } catch (SQLException e) {
            System.err.println("SQLException at getCategory()");
            response.setStatus(500);
        } catch (IOException e) {
            System.err.println("IOException at getCategory()");
        }
    }

    private void getStatistics(HttpServletRequest request, HttpServletResponse response){
        System.out.println("statistics");
        try {
            PrintWriter out = response.getWriter();

            out.println("<br><br>Μέγιστος,ελάχιστος και μέσος μισθός μόνιμου διδακτικου προσωπικού<br>");
            double min = EditPaymentsTable.MMASalaryForTypeOfEmployee("monimo_didaktiko","min");
            double max = EditPaymentsTable.MMASalaryForTypeOfEmployee("monimo_didaktiko","max");
            double avg = EditPaymentsTable.MMASalaryForTypeOfEmployee("monimo_didaktiko","avg");
            out.println("Ελάχιστος: "+min+"<br>Μέσος: "+avg+"<br>Μέγιστος: "+max);

            out.println("<br><br>Μέγιστος,ελάχιστος και μέσος μισθός μόνιμου διοικιτικού προσωπικού<br>");
            min = EditPaymentsTable.MMASalaryForTypeOfEmployee("monimo_dioikitiko","min");
            max = EditPaymentsTable.MMASalaryForTypeOfEmployee("monimo_dioikitiko","max");
            avg = EditPaymentsTable.MMASalaryForTypeOfEmployee("monimo_dioikitiko","avg");
            out.println("Ελάχιστος: "+min+"<br>Μέσος: "+avg+"<br>Μέγιστος: "+max);

            out.println("<br><br>Μέγιστος,ελάχιστος και μέσος μισθός συμβασιούχου διδακτικού προσωπικού<br>");
            min = EditPaymentsTable.MMASalaryForTypeOfEmployee("symvasiouxo_didaktiko","min");
            max = EditPaymentsTable.MMASalaryForTypeOfEmployee("symvasiouxo_didaktiko","max");
            avg = EditPaymentsTable.MMASalaryForTypeOfEmployee("symvasiouxo_didaktiko","avg");
            out.println("Ελάχιστος: "+min+"<br>Μέσος: "+avg+"<br>Μέγιστος: "+max);

            out.println("<br><br>Μέγιστος,ελάχιστος και μέσος μισθός συμβασιούχου διοικιτικού προσωπικού<br>");
            min = EditPaymentsTable.MMASalaryForTypeOfEmployee("symvasiouxo_dioikitiko","min");
            max = EditPaymentsTable.MMASalaryForTypeOfEmployee("symvasiouxo_dioikitiko","max");
            avg = EditPaymentsTable.MMASalaryForTypeOfEmployee("symvasiouxo_dioikitiko","avg");
            out.println("Ελάχιστος: "+min+"<br>Μέσος: "+avg+"<br>Μέγιστος: "+max);

        } catch (SQLException e) {
            System.err.println("SQLException at getStatistics()");
            response.setStatus(500);
        } catch (IOException e) {
            System.err.println("IOException at getStatistics()");
        }
    }

    public void avgIncrease(HttpServletRequest request, HttpServletResponse response){
        System.out.println("avgIncrease");
        String st_date = request.getParameter("st_date");
        String fin_date = request.getParameter("fin_date");

        try {
            String result = EditPaymentsTable.FindAverageIncrease(st_date,fin_date);
            PrintWriter out = response.getWriter();
            System.out.println(result);
            out.println("Μέση αύξηση μισθών και επιδωμάτων : "+result);
            response.setStatus(200);
        } catch (SQLException e) {
            System.err.println("SQLException at avgIncrease()");
            response.setStatus(500);
        } catch (IOException e) {
            System.err.println("IOException at avgIncrease()");
            response.setStatus(500);
        }
    }

    public void total(HttpServletRequest request, HttpServletResponse response){
        System.out.println("total");

        double total_mdidaktiko = 0;
        double tota_mdioikitiko = 0;
        double total_sdidaktiko = 0;
        double total_sdioikitiko = 0;

        ArrayList<Monimo_didaktiko> mdidaktiko = EditMonimoDidaktikoTable.getAllEntrys();
        for(int i=0;i<mdidaktiko.size();i++) total_mdidaktiko+=mdidaktiko.get(i).getSalary() +  mdidaktiko.get(i).getResearchAllowance() +mdidaktiko.get(i).getFamilyAllowance();
        ArrayList<Monimo_dioikitiko> mdioikitiko = EditMonimoDioikitikoTable.getAllEntrys();
        for(int i=0;i<mdioikitiko.size();i++) tota_mdioikitiko+=mdioikitiko.get(i).getSalary()+mdioikitiko.get(i).getFamilyAllowance();
        ArrayList<Symvasiouxo_didaktiko> sdidaktiko = EditSymvasiouxoDidaktikoTable.getAllEntrys();
        for(int i=0;i<sdidaktiko.size();i++) total_sdidaktiko+=sdidaktiko.get(i).getSalary()+sdidaktiko.get(i).getFamilyAllowance()+sdidaktiko.get(i).getLibAllowance();
        ArrayList<Symvasiouxo_dioikitiko> sdioikitiko = EditSymvasiouxoDioikitikoTable.getAllEntrys();
        for(int i=0;i<sdioikitiko.size();i++) total_sdioikitiko+=sdioikitiko.get(i).getSalary()+sdioikitiko.get(i).getFamilyAllowance();

        try {
            PrintWriter out = response.getWriter();
            out.println("Μόνιμο Διδακτικό Προσωπικό : "+total_mdidaktiko+"<br>");
            out.println("Μόνιμο Διοικιτικό Προσωπικό : "+tota_mdioikitiko+"<br>");
            out.println("Συμβασιούχο Διδακτικό Προσωπικό : "+total_sdidaktiko+"<br>");
            out.println("Συμβασιούχο Διοικιτικό Προσωπικό : "+total_sdioikitiko+"<br>");
            response.setStatus(200);
        } catch (IOException e) {
            System.out.println("IOEXception");
            response.setStatus(500);
        }
    }

    public void query(HttpServletRequest request, HttpServletResponse response){
        System.out.println("query");
        ResultSet rs;
        try {
            PrintWriter out = response.getWriter();
            Connection con = DB_Connection.getConnection();
            Statement stmt = con.createStatement();
            String query = request.getParameter("query");

            rs = stmt.executeQuery(query);
            while(rs.next()) out.println("<br>"+DB_Connection.getResultsToJSON(rs)+"<br>");
            response.setStatus(200);
        }
        catch (SQLException e) {
            System.err.println("SQLException in query");
            response.setStatus(500);
        }
        catch (IOException e) {
            System.err.println("IOException in query");
            response.setStatus(500);
        } catch (ClassNotFoundException e) {
            System.err.println("ClassNotFoundException in query");
            response.setStatus(500);
        }
    }
}
